package pl.devzyra.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.services.BookService;
import pl.devzyra.services.OrderService;
import pl.devzyra.services.UserService;

import java.security.Principal;

@Controller
@SessionAttributes("order")
public class OrderMvcController {

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;

    public OrderMvcController(BookService bookService, UserService userService, OrderService orderService) {
        this.bookService = bookService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @ModelAttribute("order")
    public OrderEntity getOrder() {

        return new OrderEntity();
    }


    @PostMapping("/order/add/{bookId}")
    public RedirectView addBookToOrder(@ModelAttribute("order") OrderEntity order, @PathVariable Long bookId, RedirectAttributes ra) {

        order.getBooks().add(bookService.findByBookId(bookId));


        //  ra.addFlashAttribute("books", );
        // todo: try to implement multiple redirection scenario searchMvc -> post: /order/add/{bookId} -> searchMvc

        return new RedirectView("/", true);

    }


    @GetMapping("/order")
    public String showOrder(@ModelAttribute("order") OrderEntity order, Model model) {

        model.addAttribute("orderlist", order.getBooks());

        return "orderview";
    }


    @PostMapping("/order/confirm")
    public void confirmOrder(@ModelAttribute("order") OrderEntity order, Principal principal) {



        UserEntity user = userService.getUserByEmail(principal.getName());
        order.setUser(user);
        orderService.saveOrder(order);

        // todo: impl. show Cart and confirm


    }


}
