package pl.devzyra.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
public class UserEntity implements UserDetails {

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
    public List<OrderEntity> order = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserRole role;


    public UserEntity() {
    }


    @PrePersist
    private void prePersist() {
        setRole(UserRole.USER);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getGrantedAuthority();
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
