package io.applianceskeeper.appliances.service;

import io.applianceskeeper.appliances.data.AppliancesRepository;
import io.applianceskeeper.appliances.model.Appliance;
import io.applianceskeeper.appliances.model.AppliancesWriteDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppliancesService {

    private final AppliancesRepository repository;
    private final ModelMapper mapper;

    public List<Appliance> findAll() {
        return repository.findAll();
    }

    public List<Appliance> findBySerialNumber(String serialNumber) {
        return repository.findAllBySerialNumberContainsIgnoreCaseOrderBySerialNumber(serialNumber);
    }

    public Appliance save(AppliancesWriteDTO appliancesWriteDTO) {
        return repository.save(convertToAppliance(appliancesWriteDTO));
    }

    private Appliance convertToAppliance(AppliancesWriteDTO writeDTO) {
        return mapper.map(writeDTO, Appliance.class);
    }

    public List<Appliance> findByModel(String model) {
        return repository.findAllByModel_NameContainsIgnoreCaseOrderBySerialNumber(model);
    }

    public List<Appliance> findByBrand(String brand) {
        return repository.findAllByBrand_NameContainsIgnoreCaseOrderBySerialNumber(brand);
    }

}
