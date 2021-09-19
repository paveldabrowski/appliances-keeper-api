package io.applianceskeeper.technicians.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Service
public class MonthlyCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private YearMonth month;
    private Technician technician;
    private List<TechnicianWorkingDay> workingDays;

    public MonthlyCalendar() {}

    @PostConstruct
    public void init() {
        LocalDate startDateInclusive = YearMonth.now().atDay(1);
        LocalDate endDate = YearMonth.now().atEndOfMonth();
        Period period = Period.between(startDateInclusive, endDate);
        YearMonth.now();

        int days = period.getDays();
        int i = YearMonth.now().lengthOfMonth();
        period.getUnits();


    }
}
