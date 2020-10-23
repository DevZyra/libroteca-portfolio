package pl.devzyra.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "addresses")
@Data
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String zipCode;


    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private UserEntity userDetails;

}
