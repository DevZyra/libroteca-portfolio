package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.services.BookService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static pl.devzyra.exceptions.ErrorMessages.INCORRECT_FIELDS;

@RestController
@RequestMapping("/rest/books")
public class BookRestController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    public BookRestController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookRest> createBook(@Valid @RequestBody BookRequestModel bookRequest, BindingResult result) throws BookServiceException {

        if (result.hasErrors()) {
            throw new IllegalStateException(INCORRECT_FIELDS.getErrorMessage());
        }

        BookRest returnVal = bookService.createBook(bookRequest);

        return ResponseEntity.ok(returnVal);

    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) throws UserServiceException {

        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookRest> getBookById(@PathVariable Long bookId) throws BookServiceException {

        BookRest book = bookService.getBook(bookId);

        return ResponseEntity.ok(book);
    }

    @GetMapping
    public List<BookRest> getAllBooks(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<BookRest> returnVal = new ArrayList<>();
        List<BookEntity> bookList = bookService.findAll(page, limit);
        bookList.stream().forEach(x -> {
            BookRest bookRest = new BookRest();
            bookRest = modelMapper.map(x, BookRest.class);
            returnVal.add(bookRest);
        });


        return returnVal;
    }
}
