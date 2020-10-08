package pl.devzyra.services;

import org.springframework.stereotype.Service;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.model.entities.OrderEntity;
import pl.devzyra.repositories.OrderRepository;
import pl.devzyra.utils.Utils;

import java.util.List;
import java.util.Optional;

import static pl.devzyra.exceptions.ErrorMessages.NO_RECORD_FOUND;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Utils utils;

    public OrderServiceImpl(OrderRepository orderRepository, Utils utils) {
        this.orderRepository = orderRepository;
        this.utils = utils;
    }

    @Override
    public OrderEntity saveOrder(OrderEntity order) {
        order.setOrderId(utils.generateOrderId(20));
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity getOrderById(String orderId) throws BookServiceException {
        Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity.isPresent()) {
            return orderEntity.get();
        } else {
            throw new BookServiceException(NO_RECORD_FOUND.getErrorMessage());
        }
    }

    @Override
    public List<OrderEntity> getAllOrders() {
       return orderRepository.findAll();
    }
}
