package io.applianceskeeper.appliances.service;

import io.applianceskeeper.appliances.data.BrandsRepository;
import io.applianceskeeper.appliances.model.Brand;
import io.applianceskeeper.appliances.utils.ApplianceAbstractService;
import io.applianceskeeper.utils.SortedPaginatedFiltered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandsService extends ApplianceAbstractService<Brand, Integer>
        implements SortedPaginatedFiltered<Page<Brand>> {

    private final BrandsRepository brandsRepository;

    public BrandsService(BrandsRepository repository) {
        super(repository);
        brandsRepository = repository;
    }
    @Override
    public Page<Brand> getSortedPagedFiltered(Optional<String> searchTerm, Pageable pageable) {
        return brandsRepository.findAllSortedPagedFiltered(searchTerm.orElse(""), pageable);
    }
}
