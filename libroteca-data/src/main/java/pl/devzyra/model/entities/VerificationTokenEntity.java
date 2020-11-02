package pl.devzyra.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "verification_tokens")
@Data
public class VerificationTokenEntity implements Serializable {

    private static final long serialVersionUID = 6487947528132589427L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Timestamp expirationDate;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public VerificationTokenEntity() {
    }

    public VerificationTokenEntity(UserEntity userEntity, String token) {
        this.userEntity = userEntity;
        this.token = token;
    }
}
