package io.applianceskeeper.technicians.service;

import io.applianceskeeper.appliances.model.Picture;
import io.applianceskeeper.technicians.data.PicturesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PicturesService {

    private final PicturesRepository repository;

    public Picture save(Picture picture) {
        return repository.save(picture);
    }

    public List<Picture> saveAll(Set<Picture> pictures) {
        return repository.saveAll(pictures);
    }
}
