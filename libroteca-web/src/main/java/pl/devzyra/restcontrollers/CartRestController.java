package pl.devzyra.restcontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.config.JmsConfig;
import pl.devzyra.exceptions.BookServiceException;
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

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest/cart")
public class CartRestController {

    private final UserService userService;
    private final RestCartService restCartService;
    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final OrderService orderService;
    private final JmsTemplate jmsTemplate;

    public CartRestController(UserService userService, RestCartService restCartService, ModelMapper modelMapper, BookService bookService, OrderService orderService, JmsTemplate jmsTemplate) {
        this.userService = userService;
        this.restCartService = restCartService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.orderService = orderService;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping
    public EntityModel<RestCartEntity> getCartByUsername(Principal principal) throws UserServiceException, BookServiceException {

        UserEntity user = userService.getUserByEmail(principal.getName());

        RestCartEntity cart = user.getCart();

        Link self = linkTo(methodOn(CartRestController.class).getCartByUsername(principal)).withSelfRel();
        Link selfPost = linkTo(methodOn(CartRestController.class).getCartByUsername(principal)).withRel("@post self to confirm order");
        Link books = linkTo(methodOn(BookRestController.class).getAllBooks(0, 25)).withRel("books");

        return EntityModel.of(cart, List.of(self, selfPost, books));
    }

    @PostMapping("/{bookId}")
    public EntityModel<BookEntity> addToCart(@PathVariable Long bookId, Principal principal) throws UserServiceException, BookServiceException {
        UserEntity user = userService.getUserByEmail(principal.getName());
        BookEntity book = bookService.findByBookId(bookId);

        BookEntity bookAdded = restCartService.addToCart(user, book);

        Link self = linkTo(methodOn(BookRestController.class).getBookById(bookId)).withSelfRel();
        Link books = linkTo(methodOn(BookRestController.class).getAllBooks(0, 25)).withRel("books");
        Link cart = linkTo(methodOn(CartRestController.class).getCartByUsername(principal)).withRel("cart");

        return EntityModel.of(bookAdded, List.of(self, books, cart));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderRest> confirmCartAndPlaceOrder(Principal principal) throws UserServiceException, JsonProcessingException {
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

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOrder = objectMapper.writeValueAsString(orderRest);

        jmsTemplate.convertAndSend(JmsConfig.ORDER_QUEUE, jsonOrder);

        return new ResponseEntity<>(orderRest, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}")
    public void removeFromCart(@PathVariable Long bookId, Principal principal) throws UserServiceException {
        UserEntity user = userService.getUserByEmail(principal.getName());
        BookEntity book = bookService.findByBookId(bookId);

        RestCartEntity cart = user.getCart();

        cart.getBooks().remove(book);

        restCartService.save(cart);

    }
}
