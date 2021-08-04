package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppliancesRepository extends JpaRepository<Appliance, Long> {

    List<Appliance> findAllBySerialNumberContainsIgnoreCaseOrderBySerialNumber(String serialNumber);
    List<Appliance> findAllByModel_NameContainsIgnoreCaseOrderBySerialNumber(String serialNumber);
    List<Appliance> findAllByBrand_NameContainsIgnoreCaseOrderBySerialNumber(String serialNumber);

}
