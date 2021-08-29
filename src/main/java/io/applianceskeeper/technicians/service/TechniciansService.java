package io.applianceskeeper.technicians.service;

import io.applianceskeeper.technicians.data.TechnicianRepository;
import io.applianceskeeper.technicians.models.Technician;
import io.applianceskeeper.technicians.utils.TechnicianNotFoundException;
import io.applianceskeeper.utils.SearchBySearchTerm;
import io.applianceskeeper.utils.SortedPaginatedFiltered;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TechniciansService implements SearchBySearchTerm<List<Technician>>, SortedPaginatedFiltered<Page<Technician>> {

    private final TechnicianRepository repository;

    public List<Technician> getAllByName() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Technician> findAllBySearchTerm(String searchTerm) {
        return repository.findAllBySearchTerm(searchTerm);
    }

    Technician findById(Integer id) throws TechnicianNotFoundException {
        return repository.findById(id).orElseThrow(TechnicianNotFoundException::new);
    }

    @Override
    public Page<Technician> getSortedPagedFiltered(Optional<String> searchTerm, Pageable pageable) {
        return repository.findAllSortedPagedFiltered(searchTerm.orElse(""), pageable);
    }
}
