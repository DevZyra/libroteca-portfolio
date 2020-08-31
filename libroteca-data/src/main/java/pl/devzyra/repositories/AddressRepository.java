package pl.devzyra.repositories;


import org.springframework.data.repository.CrudRepository;
import pl.devzyra.model.entities.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
}
