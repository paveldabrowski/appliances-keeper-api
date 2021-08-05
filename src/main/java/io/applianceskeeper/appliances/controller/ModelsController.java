package io.applianceskeeper.appliances.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.service.ModelsService;
import io.applianceskeeper.appliances.utils.ApplianceAbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/appliances/models")
public class ModelsController extends ApplianceAbstractController<Model, Long> {

    private final ModelsService modelsService;
    private final ObjectMapper mapper;

    ModelsController(ModelsService service, ModelsService modelsService, ObjectMapper mapper) {
        super(service);
        this.modelsService = modelsService;
        this.mapper = mapper;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveModelWithFiles(@RequestPart("model") Model model,
                                                @RequestPart("file") MultipartFile[] multipart) {
        log.info("addModelWithFiles invoked");
        Model savedModel = modelsService.saveModelWithFilesByPrefix(model, multipart);


        return ResponseEntity.ok(savedModel);
    }
}
