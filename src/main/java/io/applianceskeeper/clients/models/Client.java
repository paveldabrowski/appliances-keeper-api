package io.applianceskeeper.clients.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.applianceskeeper.appliances.model.Appliance;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "Lastname is required.")
    private String lastName;
    @Pattern(regexp = "^\\d{10}+$|^\\s*$", message = "Nip must have 10 characters")
    private String nip;
    @Pattern(regexp = "^\\d{9}+$|^\\s*$", message = "Regon must have 9 characters.")
    private String regon;
    private ClientType type;
    private String street;
    private String building;
    private String apartment;
    @Pattern(regexp = "^\\d{2}-\\d{3}$|^\\s*$", message = "Wrong zip code.")
    private String zipCode;
    private String city;
    private String phoneNumber;
    @Email(message = "Email is not valid.")
    private String email;
    private String description;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    List<Appliance> appliances;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        var client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name)
                && Objects.equals(lastName, client.lastName)
                && Objects.equals(nip, client.nip) &&
                Objects.equals(regon, client.regon) && type == client.type
                && Objects.equals(street, client.street) && Objects.equals(building, client.building)
                && Objects.equals(apartment, client.apartment) && Objects.equals(zipCode, client.zipCode)
                && Objects.equals(city, client.city) && Objects.equals(phoneNumber, client.phoneNumber)
                && Objects.equals(email, client.email) && Objects.equals(description, client.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, nip, regon, type, street, building, apartment, zipCode, city,
                phoneNumber, email, description);
    }
}

