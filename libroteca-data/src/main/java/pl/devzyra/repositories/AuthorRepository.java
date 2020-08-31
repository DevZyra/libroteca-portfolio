package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.devzyra.model.entities.AuthorEntity;

public interface AuthorRepository extends CrudRepository<AuthorEntity,Long> {
}
