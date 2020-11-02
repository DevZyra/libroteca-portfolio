package pl.devzyra.mvccontrollers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.request.OrderRequest;
import pl.devzyra.services.BookService;
import pl.devzyra.services.OrderService;
import pl.devzyra.services.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
@SessionAttributes("order")
@AllArgsConstructor
public class OrderMvcController {

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;

    @ModelAttribute("order")
    public OrderRequest getOrder() {
        // session attribute
        return new OrderRequest();
    }

    @PostMapping("/order/add/{bookId}")
    public String addBookToOrder(@ModelAttribute("order") OrderRequest order, @PathVariable Long bookId) {

        order.getBooks().add(bookService.findByBookId(bookId));


        return "index";
    }

    @GetMapping("/order")
    public String showOrder(@ModelAttribute("order") OrderRequest order, Model model) {

        model.addAttribute("orderlist", order.getBooks());
        model.addAttribute("order", order);

        return "orderview";
    }

    @PostMapping("/order/confirm")
    public String confirmOrder(@ModelAttribute("order") OrderRequest orderRequest, Principal principal, Model model, SessionStatus status) throws UserServiceException {
        Set<BookEntity> booksRequest = orderRequest.getBooks();

        OrderEntity order = new OrderEntity();
        UserEntity user = userService.getUserByEmail(principal.getName());
        order.setBooks(booksRequest);
        order.setUser(user);
        orderService.saveOrder(order);

        status.setComplete();

        model.addAttribute("order", new OrderRequest());

        return "index";
    }
}
