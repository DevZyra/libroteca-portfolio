package pl.devzyra.services;


import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.model.entities.OrderEntity;

import java.util.List;

public interface OrderService {

    OrderEntity saveOrder(OrderEntity order);

    OrderEntity getOrderById(String id) throws BookServiceException;

    List<OrderEntity> getAllOrders();
}
