package pl.devzyra.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "books")
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "books_id"),
    inverseJoinColumns = @JoinColumn(name = "authors_id"))
    @JsonManagedReference
    private Set<AuthorEntity> authors = new HashSet<>();

    @ManyToMany(mappedBy = "books")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderEntity> orders = new ArrayList<>();

    public BookEntity() {
    }

    public BookEntity(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

}
