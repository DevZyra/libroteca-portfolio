package pl.devzyra.services;

import org.springframework.stereotype.Service;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.ErrorMessages;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.repositories.OrderRepository;

import java.util.Optional;

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

    @Override
    public OrderEntity getOrderById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            return orderEntity.get();
        } else {
            throw new BookServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
    }
}
