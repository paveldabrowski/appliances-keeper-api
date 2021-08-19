package io.applianceskeeper.appliances.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "models")
@EqualsAndHashCode
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.MERGE)
//    @JsonManagedReference
    private Brand brand;
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "appliance_type_id")
    private ApplianceType applianceType;
    private String description;
    @OneToMany(mappedBy = "model", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Image> images;

}
