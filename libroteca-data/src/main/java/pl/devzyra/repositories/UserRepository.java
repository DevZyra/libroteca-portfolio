package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.devzyra.model.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
