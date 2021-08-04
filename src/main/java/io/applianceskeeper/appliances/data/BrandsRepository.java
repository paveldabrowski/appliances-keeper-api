package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.Brand;
import io.applianceskeeper.appliances.utils.ApplianceRepositoriesKeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends ApplianceRepositoriesKeeper<Brand, Integer> {

}
