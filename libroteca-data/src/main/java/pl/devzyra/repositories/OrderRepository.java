package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.OrderEntity;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
