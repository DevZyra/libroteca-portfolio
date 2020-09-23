package pl.devzyra.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.request.AuthorRequestModel;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.repositories.BookRepository;
import pl.devzyra.repositories.UserRepository;
import pl.devzyra.services.BookService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static pl.devzyra.model.entities.UserRole.ADMIN;

@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private UserRepository userRepository;
    private BookRepository bookRepository;
    private BookService bookService;
    private PasswordEncoder passwordEncoder;

    public DataLoad(UserRepository userRepository, BookRepository bookRepository, BookService bookService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        UserEntity admin = new UserEntity();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setUserId("admin");
        admin.setEmail("admin");
        admin.setEmailVerificationStatus(true);
        admin.setEncryptedPassword(passwordEncoder.encode("admin"));
        admin.setAddresses(new ArrayList<>());
        admin.setRole(ADMIN);

        userRepository.save(admin);

        // @PrePersist role setup override
        admin.setRole(ADMIN);
        userRepository.save(admin);

        loadDataifEmpty();

        alreadySetup = true;


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
