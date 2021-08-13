package io.applianceskeeper.appliances.service;

import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import io.applianceskeeper.appliances.data.ModelImagesRepository;
import io.applianceskeeper.appliances.model.Image;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.objectstorage.IBMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelImagesService {

    private final ModelImagesRepository repository;
    private final IBMService ibmService;

    public Image save(Image image) {
        return repository.save(image);
    }


    public List<Image> saveAll(Set<Image> images) {
        return repository.saveAll(images);
    }

    public List<Image> saveAll(List<S3ObjectSummary> summaryList, Model model) {
        Set<Image> images = summaryList.stream().map(s3ObjectSummary -> {
            boolean isImageExists = repository.existsByIbmKeyEqualsAndModel_Id(s3ObjectSummary.getKey(), model.getId());
            return !isImageExists ? new Image(model, s3ObjectSummary) : null;
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return repository.saveAll(images);
    }

    public List<Image> getModelPicturesWithUrls(Long id) {
        return repository.findAllByModel_Id(id).stream()
                .map(this::addUrlToModelImage)
                .collect(Collectors.toList());
    }

    private Image addUrlToModelImage(Image image) {
        image.setUrl(ibmService.getImageUrlByKey(image.getIbmKey()));
        return image;
    }
}
