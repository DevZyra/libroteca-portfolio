package pl.devzyra.model.entities;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static pl.devzyra.model.entities.UserPermission.*;

@Getter
public enum UserRole {

    ADMIN(Set.of(UserPermission.ADMIN)),
    LIBRARIAN(Set.of(BOOK_WRITE, USER_WRITE, BOOK_READ, USER_READ)),
    USER(Set.of(BOOK_READ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}
