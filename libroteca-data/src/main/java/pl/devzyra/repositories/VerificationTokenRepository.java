package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.entities.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationTokenEntity, Long> {

    VerificationTokenEntity findByToken(String token);

    VerificationTokenEntity findByUserEntity(UserEntity user);
}
