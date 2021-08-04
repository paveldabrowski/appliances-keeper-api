package io.applianceskeeper.appliances.service;

import io.applianceskeeper.appliances.data.ModelsRepository;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.utils.ApplianceAbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelsService extends ApplianceAbstractService<Model, Long> {

    private final ModelsRepository modelsRepository;

    public ModelsService(ModelsRepository repository) {
        super(repository);
        this.modelsRepository = repository;
    }

    public List<Model> findAllByBrand(String brandName) {
        return modelsRepository.findAllByBrand_NameIgnoreCaseOrderByNameAsc(brandName);
    }

    public boolean checkIfModelExistsInProvidedBrand(String modelName, int brandId) {
        return modelsRepository.existsByNameIgnoreCaseAndBrand_Id(modelName, brandId);
    }
}
