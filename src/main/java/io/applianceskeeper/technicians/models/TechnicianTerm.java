package io.applianceskeeper.technicians.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.applianceskeeper.commissions.models.Commission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "technicians_terms")
@Getter
@Setter
@RequiredArgsConstructor
public class TechnicianTerm {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Long id;
    private Hour hour;
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonBackReference("terms")
    @JoinColumn(name = "technician_working_day_id",foreignKey = @ForeignKey(name = "technician_working_day_id"),referencedColumnName = "id")
    private TechnicianWorkingDay technicianWorkingDay;

    @OneToOne(cascade = CascadeType.MERGE)
    @JsonBackReference("commission")
    private Commission commission;
    private Boolean isAvailable = true;

    @Transient
    private Long workingDayId;
    @Transient
    private Long commissionId;


    public TechnicianTerm(Hour hour, TechnicianWorkingDay technicianWorkingDay) {
        this.hour = hour;
        this.technicianWorkingDay = technicianWorkingDay;
        this.date = technicianWorkingDay.getDate();
    }

    @JsonProperty
    public Long getWorkingDayId() {
        return technicianWorkingDay != null? technicianWorkingDay.getId() : null;
    }

    @JsonProperty
    public void setWorkingDayId(Long workingDateId) {
        this.technicianWorkingDay = new TechnicianWorkingDay(workingDateId);
    }

    @JsonProperty
    public Long getCommissionId() {
        return commission != null? commission.getId() : null;
    }

    @JsonProperty
    public void setCommissionId(Long commissionId) {
        this.commission = new Commission(commissionId);
    }
}
