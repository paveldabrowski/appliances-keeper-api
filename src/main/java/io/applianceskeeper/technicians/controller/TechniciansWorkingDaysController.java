package io.applianceskeeper.technicians.controller;

import io.applianceskeeper.technicians.models.TechnicianWorkingDay;
import io.applianceskeeper.technicians.service.TechnicianWorkingDaysService;
import io.applianceskeeper.technicians.utils.TechnicianNotFoundException;
import io.applianceskeeper.technicians.utils.WorkDayNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/technicians")
public class TechniciansWorkingDaysController {

    private final TechnicianWorkingDaysService service;

    @GetMapping(value = "/{id}/workingDays")
    public ResponseEntity<?> getTechnicianWorkingDays(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(service.getWorkingDays(id));
        } catch (TechnicianNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/workingDays", params = {"workingDay"})
    public ResponseEntity<?> getTechnicianWorkingDayHours(@PathVariable("id") Integer technicianId,
                                                          @RequestParam("workingDay")
                                                          @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                                  LocalDate workingDayDate) {
        try {
            return ResponseEntity.ok(service.getTechnicianWorkingDay(technicianId, workingDayDate));
        } catch (TechnicianNotFoundException | WorkDayNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Transactional
    @PostMapping(value = "/{id}/workingDays", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addWorkingDay(@PathVariable("id") Integer technicianId,
                                           @RequestBody TechnicianWorkingDay workingDay) {
        try {
            return ResponseEntity.ok(service.checkWorkingDay(technicianId, workingDay));
        } catch (TechnicianNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
