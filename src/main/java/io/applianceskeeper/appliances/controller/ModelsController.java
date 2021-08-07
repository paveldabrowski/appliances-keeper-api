package io.applianceskeeper.appliances.controller;

import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.service.ModelsService;
import io.applianceskeeper.appliances.utils.ApplianceAbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/appliances/models")
@CrossOrigin(exposedHeaders = {"Ibm-Token"})
public class ModelsController extends ApplianceAbstractController<Model, Long> {

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
    public ResponseEntity<?> saveModelWithFiles(@RequestPart("model") Model model,
                                                @RequestPart("file") MultipartFile[] multipart) {
        log.info("ADD MODEL WITH FILES invoked");
        Model savedModel = modelsService.saveModelWithFilesByPrefix(model, multipart);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Ibm-Token", modelsService.getIbmToken());
//        httpHeaders.setAccessControlExposeHeaders(List.of("Ibm-Token", "Authorization"));
        return new ResponseEntity<>(savedModel, httpHeaders, HttpStatus.OK);
    }
}
