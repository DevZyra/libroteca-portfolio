package pl.devzyra.restcontrollers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final BookService bookService;


    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(params = "title")
    ResponseEntity<List<BookRest>> searchTitle(@RequestParam(required = false, name = "title") String title) {


        List<BookRest> returnVal = bookService.findBooksByTitle(title);

        return ResponseEntity.ok(returnVal);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @RequestMapping(params = "author")
    ResponseEntity<List<BookRest>> searchAuthor(@RequestParam(required = false, name = "author") String author) {

        List<BookRest> booksByAuthor = bookService.findBooksByAuthor(author);

        return ResponseEntity.ok(booksByAuthor);
    }

}
