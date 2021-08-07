package io.applianceskeeper.appliances.service;

import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import io.applianceskeeper.appliances.data.PicturesRepository;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.model.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Picture> saveAll(List<S3ObjectSummary> summaryList, Model model) {
        Set<Picture> pictures = summaryList.stream().map(s3ObjectSummary -> {
            boolean isPictureExists = repository.existsByIbmKeyEqualsAndAndModel_Id(s3ObjectSummary.getKey(), model.getId());
            return !isPictureExists ? new Picture(model, s3ObjectSummary) : null;
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return repository.saveAll(pictures);
    }
}
