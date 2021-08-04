package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.ApplianceType;
import io.applianceskeeper.appliances.utils.ApplianceRepositoriesKeeper;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplianceTypesRepository extends ApplianceRepositoriesKeeper<ApplianceType, Integer> {
}
