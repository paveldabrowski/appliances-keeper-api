package io.applianceskeeper.appliances.controller;

import io.applianceskeeper.appliances.model.Brand;
import io.applianceskeeper.appliances.service.BrandsService;
import io.applianceskeeper.appliances.utils.ApplianceAbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appliances/brands")
public class BrandsController extends ApplianceAbstractController<Brand, Integer> {

    BrandsController(BrandsService service) {
        super(service);
    }
}
