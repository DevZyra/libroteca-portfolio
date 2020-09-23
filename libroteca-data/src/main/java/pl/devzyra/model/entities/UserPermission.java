package pl.devzyra.model.entities;

import lombok.Getter;

@Getter
public enum UserPermission {
    BOOK_READ("book_read"),
    BOOK_WRITE("book_write"),
    USER_READ("user_read"),
    USER_WRITE("user_write"),
    ADMIN("admin");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }
}
