package io.applianceskeeper.appliances.service;

import io.applianceskeeper.appliances.data.BrandsRepository;
import io.applianceskeeper.appliances.model.Brand;
import io.applianceskeeper.appliances.utils.ApplianceAbstractService;
import org.springframework.stereotype.Service;

@Service
public class BrandsService extends ApplianceAbstractService<Brand, Integer> {

    public BrandsService(BrandsRepository repository) {
        super(repository);
    }
}
