package pl.devzyra.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "verification_tokens")
@Data
public class VerificationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Timestamp expirationDate;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
