package pl.devzyra.model.request;

import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class BookRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String title;
    @ISBN(message = "{ISBN}")
    private String isbn;
    @NotEmpty(message = "{NotEmpty}")
    private Set<AuthorRequestModel> authors;

}
