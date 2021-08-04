package io.applianceskeeper.technicians.data;

import io.applianceskeeper.technicians.models.TechnicianTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechniciansTermsRepository extends JpaRepository<TechnicianTerm, Long> {
}
