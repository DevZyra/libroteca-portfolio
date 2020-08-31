package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.devzyra.model.entities.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity,Long> {
}
