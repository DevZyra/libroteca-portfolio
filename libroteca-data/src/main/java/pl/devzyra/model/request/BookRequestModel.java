package pl.devzyra.model.request;

import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotEmpty;

public class BookRequestModel {

    @NotEmpty(message = "{NotEmpty}")
    private String title;
    @ISBN(message = "{ISBN}")
    private String isbn;

}
