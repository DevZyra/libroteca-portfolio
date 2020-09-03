package pl.devzyra.model.response;

import lombok.Data;
import pl.devzyra.model.request.AuthorRequestModel;

import java.util.HashSet;
import java.util.Set;

@Data
public class BookRest {

    private String title;
    private String isbn;
    private Set<AuthorRequestModel> authors = new HashSet<>();

}
