package io.applianceskeeper.technicians.models;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Getter
public class TechnicianWorkingDayReadDTO {

    private final Long id;
    private final LocalDate date;
    private final Integer technicianId;
    private final Set<TechnicianTerm> technicianTerms;
    private final DayOfWeek day;


    public TechnicianWorkingDayReadDTO(TechnicianWorkingDay workingDay) {
        this.id = workingDay.getId();
        this.date = workingDay.getDate();
        Technician technician = workingDay.getTechnician();
        this.technicianId = technician != null? technician.getId() : null;
        this.technicianTerms = workingDay.getTechnicianTerms();
        this.day = this.date != null ? this.date.getDayOfWeek() : null;
    }
}
