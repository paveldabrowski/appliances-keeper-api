package io.applianceskeeper.appliances.controller;

import io.applianceskeeper.appliances.model.Brand;
import io.applianceskeeper.appliances.service.BrandsService;
import io.applianceskeeper.appliances.utils.ApplianceAbstractController;
import io.applianceskeeper.utils.SortedPaginatedFiltered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/appliances/brands")
public class BrandsController extends ApplianceAbstractController<Brand, Integer>
        implements SortedPaginatedFiltered<ResponseEntity<Page<Brand>>> {


    private final BrandsService brandsService;

    BrandsController(BrandsService service) {
        super(service);
        brandsService = service;
    }

    @Override
    public ResponseEntity<Page<Brand>> getSortedPagedFiltered(Optional<String> searchTerm, Pageable pageable) {
        return ResponseEntity.ok(brandsService.getSortedPagedFiltered(searchTerm, pageable));
    }
}
