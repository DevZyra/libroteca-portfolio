package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.RestCartEntity;
import pl.devzyra.model.entities.UserEntity;

@Repository
public interface RestCartRepository extends CrudRepository<RestCartEntity, Long> {

    RestCartEntity findByUser(UserEntity user);


}
