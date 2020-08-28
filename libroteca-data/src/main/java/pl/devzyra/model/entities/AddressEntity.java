package pl.devzyra.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "addresses")
@Data
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String zipCode;
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private UserEntity userDetails;

}
