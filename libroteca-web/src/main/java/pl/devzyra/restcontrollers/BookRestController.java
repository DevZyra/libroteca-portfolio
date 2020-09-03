package pl.devzyra.restcontrollers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.services.BookService;

import javax.validation.Valid;

import static pl.devzyra.exceptions.ErrorMessages.INCORRECT_FIELDS;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookRest> createUser(@Valid @RequestBody BookRequestModel bookRequest, BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalStateException(INCORRECT_FIELDS.getErrorMessage());
        }

        BookRest returnVal = bookService.createBook(bookRequest);

        return ResponseEntity.ok(returnVal);

    }
}
