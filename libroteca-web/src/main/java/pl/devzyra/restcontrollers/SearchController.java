package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.model.entities.AuthorEntity;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.repositories.AuthorRepository;
import pl.devzyra.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;


    public SearchController(AuthorRepository authorRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(params = "title")
    ResponseEntity<List<BookRest>> searchTitle(@RequestParam(required = false, name = "title") String title) {


        List<BookEntity> booksByTitle = bookRepository.findAllByTitleContainingIgnoreCase(title);

        List<BookRest> returnVal = new ArrayList<>();

        booksByTitle.forEach(y -> {
            BookRest bookRest = modelMapper.map(y, BookRest.class);
            returnVal.add(bookRest);
        });

        return ResponseEntity.ok(returnVal);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @RequestMapping(params = "author")
    ResponseEntity<List<BookRest>> searchAuthor(@RequestParam(required = false, name = "author") String author) {

        List<AuthorEntity> authors = authorRepository.findAllByFirstNameOrLastNameContainingIgnoreCase(author, author);

        List<BookEntity> allBookByAuthorLike = new ArrayList<>();

        authors.forEach(y -> {
            allBookByAuthorLike.addAll(y.getBooks());
        });


        List<BookRest> returnVal = new ArrayList<>();

        allBookByAuthorLike.forEach(y -> {
            BookRest bookRest = modelMapper.map(y, BookRest.class);
            returnVal.add(bookRest);
        });

        return ResponseEntity.ok(returnVal);
    }

}
