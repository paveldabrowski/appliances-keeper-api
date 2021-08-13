package io.applianceskeeper.technicians.service;

import io.applianceskeeper.technicians.data.TechnicianRepository;
import io.applianceskeeper.technicians.models.Technician;
import io.applianceskeeper.technicians.utils.TechnicianNotFoundException;
import io.applianceskeeper.utils.SearchBySearchTerm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TechniciansService implements SearchBySearchTerm<List<Technician>> {

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

}
