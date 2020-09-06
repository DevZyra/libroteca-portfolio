package pl.devzyra.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.devzyra.model.request.AuthorRequestModel;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.repositories.BookRepository;
import pl.devzyra.services.BookService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoad implements CommandLineRunner {

    private BookRepository bookRepository;
    private BookService bookService;

    public DataLoad(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;

        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {

        loadDataifEmpty();


    }

    private void loadDataifEmpty() {
        AuthorRequestModel author = new AuthorRequestModel("Author01", "Author01");
        BookRequestModel bookReq = BookRequestModel.builder().isbn("978-2-12-345680-3").title("Book01").authors(Set.of(author)).build();

        AuthorRequestModel author02 = new AuthorRequestModel("Author02", "Author02");
        BookRequestModel bookReq02 = BookRequestModel.builder().isbn("978-2-12-345680-3").title("Book02").authors(Set.of(author02)).build();

        AuthorRequestModel author03 = new AuthorRequestModel("Author03", "Author03");
        BookRequestModel bookReq03 = BookRequestModel.builder().isbn("978-2-12-345680-3").title("Book03").authors(Set.of(author03)).build();

        AuthorRequestModel author04 = new AuthorRequestModel("First", "Author");
        AuthorRequestModel author05 = new AuthorRequestModel("Second", "Author");
        Set<AuthorRequestModel> authorsSet = new HashSet<>();
        authorsSet.add(author04);
        authorsSet.add(author05);
        BookRequestModel bookRequest04 = BookRequestModel.builder().isbn("978-2-12-345680-3").title("BookTwoAuthors").authors(authorsSet).build();

        if (bookRepository.findAll().isEmpty()) {
            bookService.createBook(bookReq);
            bookService.createBook(bookReq02);
            bookService.createBook(bookReq03);
            bookService.createBook(bookRequest04);

        }
    }
}
