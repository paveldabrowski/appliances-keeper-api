package io.applianceskeeper.appliances.service;

import io.applianceskeeper.appliances.data.ApplianceTypesRepository;
import io.applianceskeeper.appliances.model.ApplianceType;
import io.applianceskeeper.appliances.utils.ApplianceAbstractService;
import org.springframework.stereotype.Service;

@Service
public class ApplianceTypesService extends ApplianceAbstractService<ApplianceType, Integer> {

    ApplianceTypesService(ApplianceTypesRepository repository) {
        super(repository);
    }

}
