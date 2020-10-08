package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.model.response.OrderRest;
import pl.devzyra.model.response.UserRest;
import pl.devzyra.services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rest/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderRestController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderRest>> getAllOrders() {
        List<OrderEntity> allOrders = orderService.getAllOrders();
        List<OrderRest> listOfOrders = new ArrayList<>();

        allOrders.stream().forEach(orderEntity -> {
            Set<BookRest> booksRest = orderEntity.getBooks().stream().map(bookEntity -> modelMapper.map(bookEntity, BookRest.class)).collect(Collectors.toSet());
            UserRest userRest = modelMapper.map(orderEntity.getUser(),UserRest.class);
            OrderRest orderRest = new OrderRest();
            orderRest.setOrderId(orderEntity.getOrderId());
            orderRest.setBooks(booksRest);
            orderRest.setUserRest(userRest);
            listOfOrders.add(orderRest);
        });

        return ResponseEntity.ok(listOfOrders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderRest> getOrderById(@PathVariable String orderId) throws BookServiceException {
        OrderEntity order = orderService.getOrderById(orderId);
        OrderRest orderRest = new OrderRest();

        UserRest userRest = modelMapper.map(order.getUser(), UserRest.class);
        Set<BookRest> bookRest =  order.getBooks().stream().map(bookEntity -> modelMapper.map(bookEntity, BookRest.class)).collect(Collectors.toSet());
        orderRest.setOrderId(order.getOrderId());
        orderRest.setUserRest(userRest);
        orderRest.setBooks(bookRest);

        return ResponseEntity.ok(orderRest);
    }

}
