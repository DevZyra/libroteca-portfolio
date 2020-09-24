package pl.devzyra.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "order_book_user", joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    private Set<BookEntity> books = new HashSet<>();

    @ManyToOne
    @JoinTable(name = "order_book_user", joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private UserEntity user;

    public OrderEntity() {
    }


}
