package pl.devzyra.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.devzyra.model.entities.UserEntity;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);

}
