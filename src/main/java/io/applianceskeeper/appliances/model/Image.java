package io.applianceskeeper.appliances.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "models_images")
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ibmKey;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private Model model;
    @Transient
    private String url;

    public Image(Model model, S3ObjectSummary ibmListedObject) {
        this.model = model;
        this.ibmKey = ibmListedObject.getKey();
    }
}
