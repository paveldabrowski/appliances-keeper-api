package io.applianceskeeper.technicians.controller;

import io.applianceskeeper.technicians.models.TechnicianTerm;
import io.applianceskeeper.technicians.service.TechniciansTermsService;
import io.applianceskeeper.technicians.utils.TechnicianTermNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/technicians/terms")
public class TechniciansTermsController {

    private final TechniciansTermsService service;

    @GetMapping
    public ResponseEntity<List<TechnicianTerm>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PatchMapping
    public ResponseEntity<TechnicianTerm> updateTechnicianTerm(@RequestBody TechnicianTerm term) {
        var technicianTerm = service.update(term);
        return technicianTerm.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PatchMapping(value = "/{id}", params = {"isAvailable"})
    public ResponseEntity<?> toggleReserved(@PathVariable("id") Long id, @RequestParam("isAvailable") boolean isAvailable) {
        try {
            return ResponseEntity.ok(service.toggleReserved(id, isAvailable));
        } catch (TechnicianTermNotFoundException e) {
            return ResponseEntity.noContent().header("termNotFound", "Technician term not found")
                    .build();
        }
    }
}
