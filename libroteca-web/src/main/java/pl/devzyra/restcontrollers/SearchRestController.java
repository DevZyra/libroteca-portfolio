package pl.devzyra.restcontrollers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.services.BookService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.devzyra.exceptions.ErrorMessages.NO_RECORD_FOUND;

@RestController
@RequestMapping("rest/search")
public class SearchRestController {

    private final BookService bookService;

    public SearchRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(params = "title", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<BookRest> searchTitle(@RequestParam(required = false, name = "title") String title) throws BookServiceException {


        List<BookRest> returnVal = bookService.findBooksByTitle(title);
        if (returnVal.isEmpty()) {
            throw new BookServiceException(NO_RECORD_FOUND.getErrorMessage());
        }

        Link self = linkTo(SearchRestController.class).slash(String.format("?title=%s", title)).withSelfRel();
        Link searchTitle = linkTo(SearchRestController.class).slash("?title=").withRel("?title = { search for }");
        Link searchAuthor = linkTo(SearchRestController.class).slash("?author=").withRel("?author = { search for }");
        Link firstBook = linkTo(BookRestController.class).slash(returnVal.get(0)).withRel("first found");
        Link allBooks = linkTo(methodOn(BookRestController.class).getAllBooks(0, 25)).withRel("books");

        return CollectionModel.of(returnVal, List.of(self, searchTitle, searchAuthor, allBooks, firstBook));
    }

    @GetMapping(params = "author", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<BookRest> searchAuthor(@RequestParam(required = false, name = "author") String author) throws BookServiceException {

        List<BookRest> returnVal = bookService.findBooksByAuthor(author);

        if (returnVal.isEmpty()) {
            throw new BookServiceException(NO_RECORD_FOUND.getErrorMessage());
        }

        Link self = linkTo(SearchRestController.class).slash(String.format("?author=%s", author)).withSelfRel();
        Link searchAuthor = linkTo(SearchRestController.class).slash("?author=").withRel("?author = { search for }");
        Link searchTitle = linkTo(SearchRestController.class).slash("?title=").withRel("?title = { search for }");
        Link firstBook = linkTo(BookRestController.class).slash(returnVal.get(0)).withRel("first found");
        Link allBooks = linkTo(methodOn(BookRestController.class).getAllBooks(0, 25)).withRel("books");

        return CollectionModel.of(returnVal, List.of(self, searchAuthor, searchTitle, allBooks, firstBook));
    }

}
