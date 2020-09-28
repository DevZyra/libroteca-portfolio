package pl.devzyra.services;

import org.springframework.stereotype.Service;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }
}
