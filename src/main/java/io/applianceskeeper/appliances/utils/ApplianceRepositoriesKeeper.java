package io.applianceskeeper.appliances.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ApplianceRepositoriesKeeper<T, ID> extends JpaRepository<T, ID> {

    List<T> findAllByNameContainsIgnoreCaseOrderByName(String name);

    List<T> findAllByOrderByNameAsc();

    boolean existsByNameIgnoreCase(String name);
}
