package pl.devzyra.mvccontrollers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.services.BookService;
import pl.devzyra.services.OrderService;
import pl.devzyra.services.UserService;

import java.security.Principal;

@Controller
@SessionAttributes("order")
@AllArgsConstructor
public class OrderMvcController {

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;

    @ModelAttribute("order")
    public OrderEntity getOrder() {

        return new OrderEntity();
    }


    @PostMapping("/order/add/{bookId}")
    public String addBookToOrder(@ModelAttribute("order") OrderEntity order, @PathVariable Long bookId) {

        order.getBooks().add(bookService.findByBookId(bookId));

        return "index";

    }


    @GetMapping("/order")
    public String showOrder(@ModelAttribute("order") OrderEntity order, Model model) {

        model.addAttribute("orderlist", order.getBooks());
        model.addAttribute("order", order);

        return "orderview";
    }


    @PostMapping("/order/confirm")
    public String confirmOrder(@ModelAttribute("order") OrderEntity order, Principal principal) {


//        OrderEntity order = orderService.getOrderById(orderId);
        UserEntity user = userService.getUserByEmail(principal.getName());
        order.setUser(user);
        orderService.saveOrder(order);

        // todo: impl. show Cart and confirm
        return "index";

    }


}
