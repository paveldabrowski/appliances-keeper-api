package io.applianceskeeper.technicians.service;

import io.applianceskeeper.technicians.data.TechniciansWorkingDaysRepository;
import io.applianceskeeper.technicians.models.Hour;
import io.applianceskeeper.technicians.models.TechnicianTerm;
import io.applianceskeeper.technicians.models.TechnicianWorkingDay;
import io.applianceskeeper.technicians.utils.TechnicianNotFoundException;
import io.applianceskeeper.technicians.utils.WorkDayNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

@Service
@AllArgsConstructor
public class TechnicianWorkingDaysService {


    private final TechniciansWorkingDaysRepository repository;
    private final TechniciansService techniciansService;
    private final EntityManager entityManager;
    private final TechniciansTermsService termsService;

    @Transactional
    public TechnicianWorkingDay addWorkingDay(Integer technicianId, TechnicianWorkingDay workingDay) throws
            TechnicianNotFoundException {
        workingDay.setTechnician(techniciansService.findById(technicianId));
        var savedWorkingDay = repository.save(workingDay);
        Arrays.stream(Hour.values()).forEach(hour -> termsService.save(new TechnicianTerm(hour, savedWorkingDay)));
        entityManager.flush();
        entityManager.refresh(savedWorkingDay);
        return savedWorkingDay;
    }

    public TechnicianWorkingDay checkWorkingDay(Integer technicianId, TechnicianWorkingDay workingDay) throws
            TechnicianNotFoundException {
        try {
            return getTechnicianWorkingDay(technicianId, workingDay.getDate());
        } catch (WorkDayNotFoundException e) {
            return addWorkingDay(technicianId, workingDay);
        }
    }

    public TechnicianWorkingDay getTechnicianWorkingDay(Integer technicianId, LocalDate workingDayDate) throws
            TechnicianNotFoundException, WorkDayNotFoundException {
        var session = entityManager.unwrap(Session.class);
        var dateFilter = session.enableFilter("dateFilter");
        dateFilter.setParameter("date", workingDayDate);
        var technician = techniciansService.findById(technicianId);
        session.close();
        if (!technician.getWorkingDays().isEmpty()) {
            return (TechnicianWorkingDay) technician.getWorkingDays().toArray()[0];
        } else {
            throw new WorkDayNotFoundException();
        }
    }

    public Set<TechnicianWorkingDay> getWorkingDays(Integer id) throws TechnicianNotFoundException {
        var technician = techniciansService.findById(id);
        return technician.getWorkingDays();
    }
}
