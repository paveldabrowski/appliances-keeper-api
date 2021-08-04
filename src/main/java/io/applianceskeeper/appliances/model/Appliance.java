package io.applianceskeeper.appliances.model;

import io.applianceskeeper.clients.models.Client;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "appliances")
@AllArgsConstructor
public class Appliance {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Long id;
    private String serialNumber;
    @OneToOne(cascade = CascadeType.MERGE)
    private Model model;
    @OneToOne(cascade = CascadeType.MERGE)
    private Brand brand;
    @OneToOne(cascade = CascadeType.MERGE)
    private Client client;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appliance)) return false;
        var appliance = (Appliance) o;
        return Objects.equals(id, appliance.id) && Objects.equals(serialNumber, appliance.serialNumber) &&
                Objects.equals(model, appliance.model) && Objects.equals(brand, appliance.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, brand);
    }
}