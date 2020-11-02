package pl.devzyra.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("rest")
public class OrderRestController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;


    public OrderRestController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/orders")
    public CollectionModel<OrderRest> getAllOrders() throws BookServiceException {
        List<OrderEntity> allOrders = orderService.getAllOrders();
        List<OrderRest> listOfOrders = new ArrayList<>();

        allOrders.stream().forEach(orderEntity -> {
            Set<BookRest> booksRest = orderEntity.getBooks().stream().map(bookEntity -> modelMapper.map(bookEntity, BookRest.class)).collect(Collectors.toSet());
            UserRest userRest = modelMapper.map(orderEntity.getUser(), UserRest.class);
            OrderRest orderRest = new OrderRest();
            orderRest.setOrderId(orderEntity.getOrderId());
            orderRest.setBooks(booksRest);
            orderRest.setUserRest(userRest);
            listOfOrders.add(orderRest);
        });

        Link selfLink = linkTo(methodOn(OrderRestController.class).getAllOrders()).withSelfRel();
        Link firstOrder = linkTo(methodOn(OrderRestController.class).getOrderById(listOfOrders.get(0).getOrderId())).withRel("/{orderId} = first order");
        return CollectionModel.of(listOfOrders, List.of(selfLink, firstOrder));
    }

    @GetMapping("/orders/{orderId}")
    public EntityModel<OrderRest> getOrderById(@PathVariable String orderId) throws BookServiceException {
        OrderEntity order = orderService.getOrderById(orderId);
        OrderRest orderRest = new OrderRest();

        UserRest userRest = modelMapper.map(order.getUser(), UserRest.class);
        Set<BookRest> bookRest = order.getBooks().stream().map(bookEntity -> modelMapper.map(bookEntity, BookRest.class)).collect(Collectors.toSet());
        orderRest.setOrderId(order.getOrderId());
        orderRest.setUserRest(userRest);
        orderRest.setBooks(bookRest);

        Link self = linkTo(methodOn(OrderRestController.class).getOrderById(orderId)).withSelfRel();
        Link allOrders = linkTo(methodOn(OrderRestController.class).getAllOrders()).withRel("orders");

        return EntityModel.of(orderRest, List.of(self, allOrders));
    }

}
