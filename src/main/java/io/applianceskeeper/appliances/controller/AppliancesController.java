package io.applianceskeeper.appliances.controller;

import io.applianceskeeper.appliances.model.Appliance;
import io.applianceskeeper.appliances.model.AppliancesWriteDTO;
import io.applianceskeeper.appliances.service.AppliancesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Slf4j
@RequestMapping("/appliances")
public class AppliancesController {

    private final AppliancesService service;

    public AppliancesController(AppliancesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Appliance>> getAllAppliances() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(params = {"serialNumber"})
    public ResponseEntity<List<Appliance>> findBySerialNumber(@RequestParam("serialNumber") Optional<String> serialNumber) {
        return ResponseEntity.ok(service.findBySerialNumber(serialNumber.orElse("")));
    }

    @GetMapping(params = {"model"})
    public ResponseEntity<List<Appliance>> findByModel(@RequestParam("model") Optional<String> model) {
        return ResponseEntity.ok(service.findByModel(model.orElse("")));
    }

    @GetMapping(params = {"brand"})
    public ResponseEntity<List<Appliance>> findByBrand(@RequestParam("brand") Optional<String> brand) {
        return ResponseEntity.ok(service.findByBrand(brand.orElse("")));
    }

    @PostMapping
    public ResponseEntity<Appliance> addAppliance(@RequestBody AppliancesWriteDTO appliance) {
        var applianceFromBackend = service.save(appliance);
        return ResponseEntity.created(linkTo(AppliancesController.class).slash(applianceFromBackend.getId()).toUri())
                .body(applianceFromBackend);
    }
}
