package io.applianceskeeper.appliances.controller;

import io.applianceskeeper.appliances.model.Image;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.service.ModelsService;
import io.applianceskeeper.appliances.utils.ApplianceAbstractController;
import io.applianceskeeper.utils.SortedPaginatedFiltered;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "/appliances/models")
@CrossOrigin(exposedHeaders = {"Ibm-Token"})
public class ModelsController extends ApplianceAbstractController<Model, Long>
        implements SortedPaginatedFiltered<ResponseEntity<Page<Model>>> {

    private final ModelsService modelsService;

    ModelsController(ModelsService service, ModelsService modelsService) {
        super(service);
        this.modelsService = modelsService;
    }

    @GetMapping(params = {"brand"})
    public ResponseEntity<List<Model>> findAllByBrand(@RequestParam("brand") String brandName) {
        return ResponseEntity.ok(modelsService.findAllByBrand(brandName));
    }

    @GetMapping(params = {"nameExists", "brandId"})
    public ResponseEntity<Boolean> checkIfModelExistsInProvidedBrand(@RequestParam("nameExists") String modelName,
                                                                     @RequestParam("brandId") int brandId) {
        return ResponseEntity.ok(modelsService.checkIfModelExistsInProvidedBrand(modelName, brandId));
    }

    @PostMapping(value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_MIXED_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Model> saveModelWithFiles(@RequestPart("model") Model model,
                                                @RequestPart("file") MultipartFile[] multipart) {
        log.info("ADD MODEL WITH FILES invoked");
        Model savedModel = modelsService.saveModelWithFilesByPrefix(model, multipart);
        var headers = new HttpHeaders();
        headers.add("Ibm-Token", modelsService.getIbmToken());
        return new ResponseEntity<>(savedModel, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/images")
    public ResponseEntity<List<Image>> getModelPicturesWithUrls(@PathVariable("id") Long id) {
        var headers = new HttpHeaders();
        headers.add("Ibm-Token", modelsService.getIbmToken());
        List<Image> images = modelsService.getModelPicturesWithUrls(id);
        return new ResponseEntity<>(images, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<Model>> getSortedPagedFiltered(Optional<String> searchTerm, Pageable pageable) {
        return ResponseEntity.ok(modelsService.getSortedPagedFiltered(searchTerm, pageable));
    }
}
