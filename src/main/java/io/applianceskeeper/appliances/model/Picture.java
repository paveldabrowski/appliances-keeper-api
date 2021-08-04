package io.applianceskeeper.appliances.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "models_pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private Model model;
}
