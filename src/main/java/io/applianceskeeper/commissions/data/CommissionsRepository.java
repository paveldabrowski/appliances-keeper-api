package io.applianceskeeper.commissions.data;

import io.applianceskeeper.commissions.models.Commission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommissionsRepository extends JpaRepository<Commission, Long> {

    List<Commission> findAllByClientId(Long id);

    @Query(nativeQuery = true, value = "select * from commissions com join appliances a on com.appliance_id = a.id " +
            "join clients cl on com.client_id = cl.id " +
            "join technicians t on t.id = com.technician_id " +
            "join models m on a.model_id = m.id " +
            "join brands b on a.brand_id = b.id " +
            "where cast(com.id as varchar) like lower(concat('%', :searchTerm, '%')) " +
            "or cast(com.client_id as varchar) like lower(concat('%', :searchTerm, '%'))"  +
            "or cast(com.appliance_id as varchar) like lower(concat('%', :searchTerm, '%'))"  +
            "or cast(com.creation_date as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(com.problem_description) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(com.advice_given as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(com.technician_id as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(com.repair_date_id as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(com.technician_report) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(com.solution_description) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(com.client_visited as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(com.commission_status as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.serial_number) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(m.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(b.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(t.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(t.last_name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(t.phone_number) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(cl.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(cl.last_name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(cl.email) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(cl.phone_number) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(cl.nip) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(cl.regon) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(cl.zip_code) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(cl.id as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(t.id as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(a.id as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(b.id as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(a.id as varchar) like lower(concat('%', :searchTerm, '%'))"
    )
    Page<Commission> findAll(@Param("searchTerm") String searchTerm, Pageable pageable);
}
