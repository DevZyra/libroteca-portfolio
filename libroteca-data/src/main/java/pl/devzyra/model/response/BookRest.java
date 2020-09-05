package pl.devzyra.model.response;

import lombok.Data;
import pl.devzyra.model.entities.AuthorEntity;

import java.util.HashSet;
import java.util.Set;

@Data
public class BookRest {

    private String title;
    private String isbn;
    private Set<AuthorEntity> authors = new HashSet<>();

}
