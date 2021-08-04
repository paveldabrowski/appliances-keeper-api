package io.applianceskeeper.technicians.data;

import io.applianceskeeper.technicians.models.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {

    List<Technician> findAllByOrderByNameAsc();

    @Query(nativeQuery = true, value = "select * from technicians t " +
            "where cast(t.id as varchar) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(t.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(t.last_name) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(t.phone_number as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "ORDER BY name"
    )
    List<Technician> findAllBySearchTerm(@Param("searchTerm") String searchTerm);
}
