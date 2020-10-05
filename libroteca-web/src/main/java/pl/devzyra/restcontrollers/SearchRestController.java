package pl.devzyra.restcontrollers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.services.BookService;

import java.util.List;

import static pl.devzyra.exceptions.ErrorMessages.NO_RECORD_FOUND;

@RestController
@RequestMapping("rest/search")
public class SearchRestController {

    private final BookService bookService;


    public SearchRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(params = "title",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<BookRest>> searchTitle(@RequestParam(required = false, name = "title") String title) throws BookServiceException {


        List<BookRest> returnVal = bookService.findBooksByTitle(title);
        if(returnVal.isEmpty()){
            throw new BookServiceException(NO_RECORD_FOUND.getErrorMessage());
        }

        return ResponseEntity.ok(returnVal);
    }

    @GetMapping(params = "author",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<BookRest>> searchAuthor(@RequestParam(required = false, name = "author") String author) {

        List<BookRest> booksByAuthor = bookService.findBooksByAuthor(author);

        return ResponseEntity.ok(booksByAuthor);
    }

}
