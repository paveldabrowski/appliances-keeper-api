package io.applianceskeeper.commissions.service;

import io.applianceskeeper.commissions.data.CommissionsRepository;
import io.applianceskeeper.commissions.models.Commission;
import io.applianceskeeper.commissions.utils.CommissionNotFound;
import io.applianceskeeper.technicians.service.TechniciansTermsService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommissionService {

    private final CommissionsRepository repository;
    private final TechniciansTermsService termsService;


    @Transactional
    public Commission save(Commission commission) {
        var savedCommission = repository.save(commission);
        var term = termsService.findById(commission.getRepairDate().getId());
        term.setCommission(savedCommission);
        return savedCommission;
    }

    public List<Commission> findAll() {
        return repository.findAll();
    }
    public Commission findById(Long id) throws NotFoundException {
        Optional<Commission> commission = repository.findById(id);
        if (commission.isPresent())
            return commission.get();
        else throw new NotFoundException("Commission not found");
    }

    public List<Commission> getAllCommissionsByClientId(Long id) throws NotFoundException {
       var commissions = repository.findAllByClientId(id);
       if (!commissions.isEmpty())
           return commissions;
       else throw new NotFoundException("Commissions or client not found.");
    }

    public Page<Commission> getSortedPagedFilteredCommissions(String searchTerm, Pageable pageable) {
        return repository.findAll(searchTerm, pageable);
    }

    public Long getCommissionsCount() {
        return repository.count();
    }

    @Transactional
    public void deleteCommission(Long id) throws CommissionNotFound {
        var optionalCommission = repository.findById(id);
        if (optionalCommission.isPresent()) {
            var commission = optionalCommission.get();
            if (commission.getRepairDate() != null) {
                termsService.releaseTerm(commission.getRepairDate().getId());
            }
            repository.delete(commission);
        } else
            throw new CommissionNotFound();
    }
}
