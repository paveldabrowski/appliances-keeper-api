package io.applianceskeeper.appliances.data;

import io.applianceskeeper.appliances.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelImagesRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByIbmKeyEqualsAndModel_Id(String ibmKey, Long id);

    boolean existsByIbmKeyEqualsAndModel_Id(String ibmKey, Long id);

    List<Image> findAllByModel_Id(Long id);

}
