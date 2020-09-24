package pl.devzyra.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.repositories.OrderRepository;
import pl.devzyra.repositories.UserRepository;
import pl.devzyra.services.BookService;
import pl.devzyra.services.OrderService;

import java.security.Principal;

@Controller
@SessionAttributes("order")
public class OrderMvcController {

    private final BookService bookService;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public OrderMvcController(BookService bookService, UserRepository userRepository, OrderRepository orderRepository, OrderService orderService) {
        this.bookService = bookService;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @ModelAttribute("order")
    public OrderEntity getOrder() {

        return new OrderEntity();
    }


    @GetMapping("/order/add/{bookId}")
    public String addBookToOrder(@ModelAttribute("order") OrderEntity order, @PathVariable Long bookId, Principal principal) {


        UserEntity user = userRepository.findByEmail(principal.getName());

        order.setUser(user);
        order.getBooks().add(bookService.findByBookId(bookId));


        return "index";
    }


    @GetMapping("/order")
    public String showOrder(@ModelAttribute("order") OrderEntity order, Model model) {

        model.addAttribute("orderlist", order.getBooks());

        return "orderview";
    }


    @PostMapping("/order/confirm")
    public void confirmOrder(@ModelAttribute("order") OrderEntity order) {

        orderService.saveOrder(order);

        // todo: impl. show Cart and confirm


    }


}
