package pl.devzyra.services;


import pl.devzyra.model.entities.OrderEntity;

public interface OrderService {

    OrderEntity saveOrder(OrderEntity order);

    OrderEntity getOrderById(Long id);
}
