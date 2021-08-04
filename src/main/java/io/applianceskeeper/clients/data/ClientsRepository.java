package io.applianceskeeper.clients.data;

import io.applianceskeeper.clients.models.Client;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {

    void deleteById(@NonNull Long id) throws EmptyResultDataAccessException;

    List<Client> findAllByOrderByNameAsc();

    @Query(nativeQuery = true, value = "select * from clients c " +
            "where cast(c.id as varchar) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(c.last_name) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(c.regon as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(c.nip as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(c.phone_number as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(c.zip_code as varchar) like lower(concat('%', :searchTerm, '%'))" +
            "or cast(c.email as varchar) like lower(concat('%', :searchTerm, '%'))" +
            " ORDER BY name ASC"
    )
    List<Client> findAllBySearchTerm(@Param("searchTerm") String searchTerm);
}
