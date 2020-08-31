package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.devzyra.model.entities.VerificationTokenEntity;

public interface VerificationTokenRepository extends CrudRepository<VerificationTokenEntity,Long> {
}
