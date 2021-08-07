package io.applianceskeeper.appliances.service;

import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import io.applianceskeeper.appliances.data.ModelsRepository;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.utils.ApplianceAbstractService;
import io.applianceskeeper.objectstorage.IBMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ModelsService extends ApplianceAbstractService<Model, Long> {

    private final ModelsRepository modelsRepository;
    private final IBMService ibmService;
    private final EntityManager entityManager;
    private final PicturesService picturesService;

    public ModelsService(ModelsRepository repository, IBMService ibmService, EntityManager entityManager,
                         PicturesService picturesService) {
        super(repository);
        this.modelsRepository = repository;
        this.ibmService = ibmService;
        this.entityManager = entityManager;
        this.picturesService = picturesService;
    }

    public List<Model> findAllByBrand(String brandName) {
        return modelsRepository.findAllByBrand_NameIgnoreCaseOrderByNameAsc(brandName);
    }

    public boolean checkIfModelExistsInProvidedBrand(String modelName, int brandId) {
        return modelsRepository.existsByNameIgnoreCaseAndBrand_Id(modelName, brandId);
    }

    @Transactional
    public Model saveModelWithFilesByPrefix(Model model, MultipartFile[] multipart) {
        Optional<File> directory = createDirectory(multipart);
        Model savedModel = modelsRepository.save(model);
        if (directory.isPresent()) {
            var id = savedModel.getId();
            ibmService.uploadDirectoryWithPrefix(id.toString(), directory.get());
            List<S3ObjectSummary> pictures = ibmService.getPicturesByPrefix(savedModel);
            picturesService.saveAll(pictures, model);
        }
        entityManager.flush();
        entityManager.refresh(savedModel);

        return savedModel;
    }

    private Optional<File> createDirectory(MultipartFile[] multipart) {
        File directory;
        try {
            directory = Files.createTempDirectory("tempdir").toFile();
            log.info("Temporary directory created");
        } catch (IOException e) {
            log.error("Error while creating directory: " + e.getMessage());
            return Optional.empty();
        }

        if (directory.isDirectory()) {
            Arrays.stream(multipart).forEach(multipartFile -> {
                File file = new File(directory, Objects.requireNonNull(multipartFile.getOriginalFilename()));
                try {
                    if (file.createNewFile())
                        multipartFile.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return Optional.of(directory);
    }

    public String getIbmToken() {
        return ibmService.getToken();
    }
}
