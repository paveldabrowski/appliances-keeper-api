package io.applianceskeeper.commissions.controller;

import io.applianceskeeper.commissions.models.Commission;
import io.applianceskeeper.commissions.service.CommissionService;
import io.applianceskeeper.commissions.utils.CommissionNotFound;
import io.applianceskeeper.technicians.utils.TechnicianTermNotFoundException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@RequestMapping("/commissions")
public class CommissionsController {

    private final CommissionService service;

    public CommissionsController(CommissionService service) {
        this.service = service;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Commission> updateCommission(@RequestBody Commission commission) {
        return ResponseEntity.ok(service.save(commission));
    }

    @GetMapping(params = {"!searchTerm", "!sort", "!page", "!size"})
    public ResponseEntity<List<Commission>> getAllCommissions() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<Commission>> getSortedPagedFilteredCommissions(@RequestParam("searchTerm") Optional<String> searchTerm,
                                                                              Pageable pageable) {
        return ResponseEntity.ok(service.getSortedPagedFilteredCommissions(searchTerm.orElse(""), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commission> getCommissionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Commission>> getAllCommissionsByClientId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getAllCommissionsByClientId(id));
        } catch (NotFoundException e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCommissionsCount() {
        return ResponseEntity.ok(service.getCommissionsCount());
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<Commission> addCommission(@RequestBody Commission commission) {
        var commissionFromBackend = service.save(commission);
        return ResponseEntity.created(linkTo(CommissionsController.class).slash(commissionFromBackend.getId()).toUri())
                .body(commissionFromBackend);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommission(@PathVariable("id") Long id) throws TechnicianTermNotFoundException {
        try {
            service.deleteCommission(id);
            return ResponseEntity.noContent().build();
        } catch (CommissionNotFound commissionNotFound) {
            log.error(commissionNotFound.getMessage());
        }
        return ResponseEntity.notFound().build();
    }
}

