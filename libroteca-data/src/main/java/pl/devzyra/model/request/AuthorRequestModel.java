package pl.devzyra.model.request;

import javax.validation.constraints.NotEmpty;

public class AuthorRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String firstName;
    @NotEmpty(message = "{NotEmpty}")
    private String lastName;

}
