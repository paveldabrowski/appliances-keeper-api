

package io.applianceskeeper.technicians.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "technicians")
public class Technician {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;
    private String name;
    private String lastName;
    private String phoneNumber;
    @OneToMany(mappedBy = "technician")
    @ToString.Exclude
    @Filters(value = {@Filter(name = "dateFilter"), @Filter(name = "dateFilterBetween")})
    @JsonManagedReference("days")
    private Set<TechnicianWorkingDay> workingDays;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Technician)) return false;
        Technician that = (Technician) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(workingDays, that.workingDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, phoneNumber, workingDays);
    }
}
