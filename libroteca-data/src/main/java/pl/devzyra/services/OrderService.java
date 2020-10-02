package pl.devzyra.services;


import pl.devzyra.model.entities.OrderEntity;

import java.util.List;

public interface OrderService {

    OrderEntity saveOrder(OrderEntity order);

    OrderEntity getOrderById(Long id);

    List<OrderEntity> getAllOrders();
}
