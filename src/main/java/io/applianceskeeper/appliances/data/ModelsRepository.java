package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.utils.ApplianceRepositoriesKeeper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelsRepository extends ApplianceRepositoriesKeeper<Model, Long> {

    List<Model> findAllByBrand_NameIgnoreCaseOrderByNameAsc(String serialNumber);

    boolean existsByNameIgnoreCaseAndBrand_Id(String modelName, int id);

    @Query(nativeQuery = true, value = "select * from models m join brands b on m.brand_id = b.id " +
            "join appliances_types a on a.id = m.appliance_type_id " +
            "where cast(m.id as varchar) like lower(concat('%', :searchTerm, '%'))"  +
            "or lower(m.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(m.description) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(b.id as varchar) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(b.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.name) like lower(concat('%', :searchTerm, '%'))"
    )
    Page<Model> findAllSortedPagedFiltered(@Param("searchTerm") String searchTerm, Pageable pageable);

}
