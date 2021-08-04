package io.applianceskeeper.appliances.controller;

import io.applianceskeeper.appliances.model.ApplianceType;
import io.applianceskeeper.appliances.service.ApplianceTypesService;
import io.applianceskeeper.appliances.utils.ApplianceAbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appliances/types")
public class ApplianceTypesController extends ApplianceAbstractController<ApplianceType, Integer> {

    ApplianceTypesController(ApplianceTypesService service) {
        super(service);
    }
}
