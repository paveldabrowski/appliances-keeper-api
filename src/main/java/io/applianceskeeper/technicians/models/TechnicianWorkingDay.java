package io.applianceskeeper.technicians.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@FilterDefs(value = {
        @FilterDef(name = "dateFilterBetween",
        parameters = {
                @ParamDef(name = "minDate", type = "java.time.LocalDate"),
                @ParamDef(name = "maxDate", type = "java.time.LocalDate")
        }, defaultCondition = "date >= :minDate and date <= :maxDate"),
        @FilterDef(name = "dateFilter",
                parameters = {
                        @ParamDef(name = "date", type = "java.time.LocalDate")
                }, defaultCondition = "date = :date")}

)
@Getter
@Setter
@Entity
@Table(name = "technicians_calendar")
@RequiredArgsConstructor
public class TechnicianWorkingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @ManyToOne
    @JsonBackReference("days")
    private Technician technician;
    @OneToMany(mappedBy = "technicianWorkingDay")
    @JsonManagedReference("terms")
    @OrderBy
    private Set<TechnicianTerm> technicianTerms;


    public TechnicianWorkingDay(Long id) {
        this.id = id;
    }
}
