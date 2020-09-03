package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationTokenEntity, Long> {
}
