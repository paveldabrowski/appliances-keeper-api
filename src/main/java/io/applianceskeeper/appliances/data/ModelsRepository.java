package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.utils.ApplianceRepositoriesKeeper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelsRepository extends ApplianceRepositoriesKeeper<Model, Long> {

    List<Model> findAllByBrand_NameIgnoreCaseOrderByNameAsc(String serialNumber);

    boolean existsByNameIgnoreCaseAndBrand_Id(String modelName, int id);

}
