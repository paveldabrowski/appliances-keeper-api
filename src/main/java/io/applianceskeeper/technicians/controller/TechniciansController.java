package io.applianceskeeper.technicians.controller;

import io.applianceskeeper.technicians.models.Technician;
import io.applianceskeeper.technicians.service.TechniciansService;
import io.applianceskeeper.utils.SearchBySearTerm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/technicians")
@AllArgsConstructor
public class TechniciansController implements SearchBySearTerm<ResponseEntity<List<Technician>>> {

    private final TechniciansService service;


    @GetMapping
    public ResponseEntity<List<Technician>> getAllByName() {
        return ResponseEntity.ok(service.getAllByName());
    }

    @GetMapping(params = {"searchTerm"})
    @Override
    public ResponseEntity<List<Technician>> findAllBySearchTerm(@RequestParam("searchTerm") String searchTerm) {
        return ResponseEntity.ok(service.findAllBySearchTerm(searchTerm));
    }
}
