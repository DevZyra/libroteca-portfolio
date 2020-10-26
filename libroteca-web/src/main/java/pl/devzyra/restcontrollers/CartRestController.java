package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.model.entities.RestCartEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.response.OrderRest;
import pl.devzyra.model.response.UserRest;
import pl.devzyra.services.BookService;
import pl.devzyra.services.OrderService;
import pl.devzyra.services.RestCartService;
import pl.devzyra.services.UserService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/cart")
public class CartRestController {

    private final UserService userService;
    private final RestCartService restCartService;
    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final OrderService orderService;

    public CartRestController(UserService userService, RestCartService restCartService, ModelMapper modelMapper, BookService bookService, OrderService orderService) {
        this.userService = userService;
        this.restCartService = restCartService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<RestCartEntity> getCartByUsername(Principal principal) throws UserServiceException {

        UserEntity user = userService.getUserByEmail(principal.getName());

        RestCartEntity cart = user.getCart();

        return ResponseEntity.of(Optional.of(cart));
    }

    @Transactional
    @PostMapping("/{bookId}")
    public ResponseEntity<BookEntity> addToCart(@PathVariable Long bookId, Principal principal) throws UserServiceException {
        UserEntity user = userService.getUserByEmail(principal.getName());
        BookEntity book = bookService.findByBookId(bookId);

        BookEntity bookAdded = restCartService.addToCart(user, book);

        return ResponseEntity.of(Optional.of(bookAdded));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderRest> confirmCartAndPlaceOrder(Principal principal) throws UserServiceException {
        UserEntity user = userService.getUserByEmail(principal.getName());
        List<BookEntity> books = user.getCart().getBooks();

        OrderEntity order = new OrderEntity();
        order.setBooks(new HashSet<>(books));
        order.setUser(user);

        orderService.saveOrder(order);

        user.getCart().getBooks().removeAll(books);
        userService.save(user);

        OrderRest orderRest = modelMapper.map(order, OrderRest.class);
        orderRest.setUserRest(modelMapper.map(user, UserRest.class));

        return new ResponseEntity<>(orderRest, HttpStatus.CREATED);
    }
}
