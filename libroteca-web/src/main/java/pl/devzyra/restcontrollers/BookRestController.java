package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public EntityModel<BookRest> createBook(@Valid @RequestBody BookRequestModel bookRequest, BindingResult result) throws BookServiceException {

        if (result.hasErrors()) {
            throw new IllegalStateException(INCORRECT_FIELDS.getErrorMessage());
        }

        BookRest returnVal = bookService.createBook(bookRequest);


        Link self = linkTo(methodOn(BookRestController.class).getBookById(returnVal.getId())).withSelfRel();
        Link allBooks = linkTo(methodOn(BookRestController.class).getAllBooks(0, 25)).withRel("books");


        return EntityModel.of(returnVal, List.of(self, allBooks));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) throws UserServiceException {

        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}")
    public EntityModel<BookRest> getBookById(@PathVariable Long bookId) throws BookServiceException {

        BookRest book = bookService.getById(bookId);


        Link self = linkTo(methodOn(BookRestController.class).getBookById(book.getId())).withSelfRel();
        Link allBooks = linkTo(methodOn(BookRestController.class).getAllBooks(0, 25)).withRel("books");
        Link addToCart = linkTo(CartRestController.class).slash(bookId).withRel("addToCart: @post /rest/cart/{bookId}");


        return EntityModel.of(book, List.of(self, allBooks, addToCart));
    }

    @GetMapping
    public CollectionModel<BookRest> getAllBooks(@RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "limit", defaultValue = "25") int limit) throws BookServiceException {
        List<BookRest> returnVal = new ArrayList<>();
        List<BookEntity> bookList = bookService.findAll(page, limit);
        bookList.stream().forEach(x -> {
            BookRest bookRest = modelMapper.map(x, BookRest.class);
            returnVal.add(bookRest);
        });

        List<Link> hateoas = new ArrayList<>();

        Link self = linkTo(methodOn(BookRestController.class).getAllBooks(0, 25)).withSelfRel();
        hateoas.add(self);

        if (!returnVal.isEmpty()) {
            Link first = linkTo(methodOn(BookRestController.class).getBookById(returnVal.get(0).getId())).withRel("/{bookId}");
            hateoas.add(first);
        }

        return CollectionModel.of(returnVal, hateoas);
    }
}
