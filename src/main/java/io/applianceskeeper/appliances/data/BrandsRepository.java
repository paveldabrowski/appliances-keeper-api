package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.Brand;
import io.applianceskeeper.appliances.utils.ApplianceRepositoriesKeeper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends ApplianceRepositoriesKeeper<Brand, Integer> {


    @Query(nativeQuery = true, value = "select * from brands b " +
            "where cast(b.id as varchar) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(b.name) like lower(concat('%', :searchTerm, '%'))"
    )
    Page<Brand> findAllSortedPagedFiltered(@Param("searchTerm") String searchTerm, Pageable pageable);

}
