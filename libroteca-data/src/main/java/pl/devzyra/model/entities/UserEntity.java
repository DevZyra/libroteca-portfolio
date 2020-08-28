package pl.devzyra.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;            // Sensitive data , database id
    private String userId;      // Safe userId that can be shared
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;

    @OneToOne
    private VerificationTokenEntity emailVerificationToken;

    private Boolean emailVerificationStatus = false;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AddressEntity> addresses;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> order;
}
