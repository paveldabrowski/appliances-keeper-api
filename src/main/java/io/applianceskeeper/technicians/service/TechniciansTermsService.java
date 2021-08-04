package io.applianceskeeper.technicians.service;

import io.applianceskeeper.technicians.data.TechniciansTermsRepository;
import io.applianceskeeper.technicians.models.TechnicianTerm;
import io.applianceskeeper.technicians.utils.TechnicianTermNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TechniciansTermsService {

    private final TechniciansTermsRepository repository;

    public TechnicianTerm save(TechnicianTerm technicianTerm) {
        return repository.save(technicianTerm);
    }

    public Optional<TechnicianTerm> update(TechnicianTerm term) {
        try {
            return Optional.of(repository.save(term));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    public List<TechnicianTerm> findAll() {
        return repository.findAll();
    }

    @Transactional
    public TechnicianTerm toggleReserved(Long id, boolean isAvailable) throws TechnicianTermNotFoundException {
        var searchedTerm = repository.findById(id);
        if (searchedTerm.isPresent()) {
            var term = searchedTerm.get();
            term.setIsAvailable(isAvailable);
            return term;
        }
        throw new TechnicianTermNotFoundException();
    }

    public TechnicianTerm findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void releaseTerm(Long termId) {
        var optionalTerm = repository.findById(termId);
        if (optionalTerm.isPresent()) {
            var term = optionalTerm.get();
            term.setIsAvailable(true);
            term.setCommission(null);
        }
    }
}
