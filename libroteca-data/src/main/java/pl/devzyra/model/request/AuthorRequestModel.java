package pl.devzyra.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthorRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String firstName;
    @NotEmpty(message = "{NotEmpty}")
    private String lastName;

    public AuthorRequestModel() {
    }

    public AuthorRequestModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
