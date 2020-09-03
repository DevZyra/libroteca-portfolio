package pl.devzyra.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.AddressEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
}
