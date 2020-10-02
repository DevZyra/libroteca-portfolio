package pl.devzyra.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("rest/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }


}
