package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PicturesRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findByIbmKeyEqualsAndModel_Id(String ibmKey, Long id);

    boolean existsByIbmKeyEqualsAndAndModel_Id(String ibmKey, Long id);

}
