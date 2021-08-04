package io.applianceskeeper.technicians.data;

import io.applianceskeeper.technicians.models.TechnicianWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechniciansWorkingDaysRepository extends JpaRepository<TechnicianWorkingDay, Long> {


}
