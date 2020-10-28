package pl.devzyra.bootstrap;

import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.model.entities.RestCartEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.request.AuthorRequestModel;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.repositories.BookRepository;
import pl.devzyra.repositories.RestCartRepository;
import pl.devzyra.repositories.UserRepository;
import pl.devzyra.services.BookService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static pl.devzyra.model.entities.UserRole.*;
import static pl.devzyra.model.entities.UserRole.ADMIN;

@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private static final String ADMIN_STR = "admin";
    private static final String USER_STR = "user";

    private UserRepository userRepository;
    private BookRepository bookRepository;
    private BookService bookService;
    private PasswordEncoder passwordEncoder;
    private RestCartRepository restCartRepository;

    public DataLoad(UserRepository userRepository, BookRepository bookRepository, BookService bookService, PasswordEncoder passwordEncoder, RestCartRepository restCartRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.passwordEncoder = passwordEncoder;
        this.restCartRepository = restCartRepository;
    }

    @Transactional
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;


        UserEntity admin = new UserEntity();
        admin.setFirstName(ADMIN_STR);
        admin.setLastName(ADMIN_STR);
        admin.setUserId(ADMIN_STR);
        admin.setEmail(ADMIN_STR);
        admin.setEmailVerificationStatus(true);
        admin.setEncryptedPassword(passwordEncoder.encode(ADMIN_STR));
        admin.setAddresses(new ArrayList<>());
        admin.setRole(ADMIN);

        RestCartEntity adminCart = new RestCartEntity();
        adminCart.setCartId(String.valueOf(UUID.randomUUID()));
        admin.setCart(adminCart);
        adminCart.setUser(admin);
        restCartRepository.save(adminCart);

        userRepository.save(admin);

        // @PrePersist role setup override
        admin.setRole(ADMIN);
        userRepository.save(admin);

        UserEntity user = new UserEntity();
        user.setFirstName(USER_STR);
        user.setLastName(USER_STR);
        user.setUserId(USER_STR);
        user.setEmail(USER_STR);
        user.setEmailVerificationStatus(true);
        user.setEncryptedPassword(passwordEncoder.encode(USER_STR));
        user.setAddresses(new ArrayList<>());
        user.setRole(USER);

        RestCartEntity userCart = new RestCartEntity();
        userCart.setCartId(String.valueOf(UUID.randomUUID()));
        user.setCart(userCart);
        userCart.setUser(user);
        restCartRepository.save(userCart);

        userRepository.save(user);

        loadDataifEmpty();

        alreadySetup = true;


    }

    private void loadDataifEmpty() throws BookServiceException {

        String isbn = "978-2-12-345680-3";

        AuthorRequestModel author = new AuthorRequestModel("Author01", "Author01");
        BookRequestModel bookReq = BookRequestModel.builder().isbn(isbn).title("Book01").authors(Set.of(author)).build();

        AuthorRequestModel author02 = new AuthorRequestModel("Author02", "Author02");
        BookRequestModel bookReq02 = BookRequestModel.builder().isbn(isbn).title("Book02").authors(Set.of(author02)).build();

        AuthorRequestModel author03 = new AuthorRequestModel("Author03", "Author03");
        BookRequestModel bookReq03 = BookRequestModel.builder().isbn(isbn).title("Book03").authors(Set.of(author03)).build();

        AuthorRequestModel author04 = new AuthorRequestModel("First", "Author");
        AuthorRequestModel author05 = new AuthorRequestModel("Second", "Author");
        Set<AuthorRequestModel> authorsSet = new HashSet<>();
        authorsSet.add(author04);
        authorsSet.add(author05);
        BookRequestModel bookRequest04 = BookRequestModel.builder().isbn(isbn).title("BookTwoAuthors").authors(authorsSet).build();

        if (bookRepository.findAll().isEmpty()) {
            bookService.createBook(bookReq);
            bookService.createBook(bookReq02);
            bookService.createBook(bookReq03);
            bookService.createBook(bookRequest04);

        }
    }


}
