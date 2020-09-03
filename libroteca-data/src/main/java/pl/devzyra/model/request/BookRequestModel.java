package pl.devzyra.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class BookRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String title;
    @ISBN(message = "{ISBN}")
    private String isbn;
    @NotEmpty(message = "{NotEmpty}")
    private Set<AuthorRequestModel> authors = new HashSet<>();

}
