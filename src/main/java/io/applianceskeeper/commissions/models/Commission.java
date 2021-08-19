package io.applianceskeeper.commissions.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.applianceskeeper.appliances.model.Appliance;
import io.applianceskeeper.clients.models.Client;
import io.applianceskeeper.technicians.models.Technician;
import io.applianceskeeper.technicians.models.TechnicianTerm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "commissions")
@Entity
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "appliance_id")
    private Appliance appliance;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    private String problemDescription;
    private boolean adviceGiven;
    @OneToOne
    private Technician technician;
    @OneToOne(cascade = CascadeType.MERGE)
    @JsonManagedReference("commission")
    private TechnicianTerm repairDate;
    private String technicianReport;
    private String solutionDescription;
    private boolean clientVisited;
    private boolean commissionStatus;
    @ManyToOne
    private Client client;

    public Commission(Long commissionId) {
        this.id = commissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commission)) return false;
        Commission that = (Commission) o;
        return adviceGiven == that.adviceGiven && clientVisited == that.clientVisited
                && commissionStatus == that.commissionStatus && Objects.equals(id, that.id)
                && Objects.equals(appliance, that.appliance) && Objects.equals(creationDate, that.creationDate)
                && Objects.equals(problemDescription, that.problemDescription)
                && Objects.equals(technician, that.technician) && Objects.equals(repairDate, that.repairDate)
                && Objects.equals(technicianReport, that.technicianReport) && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appliance, creationDate, problemDescription, adviceGiven, technician, repairDate,
                technicianReport, clientVisited, commissionStatus, client);
    }
}
