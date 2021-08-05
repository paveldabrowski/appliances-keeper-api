package io.applianceskeeper.technicians.data;

import io.applianceskeeper.appliances.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PicturesRepository extends JpaRepository<Picture, Long> {
}
