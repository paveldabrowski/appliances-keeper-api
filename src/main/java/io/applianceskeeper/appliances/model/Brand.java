package io.applianceskeeper.appliances.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
//    @OneToMany(mappedBy = "brand")
//    @JsonManagedReference
//    @ToString.Exclude
//    private List<Model> modelList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;
        var brand = (Brand) o;
        return Objects.equals(id, brand.id) && Objects.equals(name, brand.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
