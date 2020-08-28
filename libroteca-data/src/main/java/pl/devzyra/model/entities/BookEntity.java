package pl.devzyra.model.entities;

import lombok.Data;

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

    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "books_id"),
    inverseJoinColumns = @JoinColumn(name = "authors_id"))
    private Set<AuthorEntity> authors = new HashSet<>();

    @ManyToMany(mappedBy = "books")
    private List<OrderEntity> orders = new ArrayList<>();

    public BookEntity() {
    }

    public BookEntity(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

}
