package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.AuthorEntity;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    List<AuthorEntity> findAllByFirstNameOrLastNameContainingIgnoreCase(String firstName, String lastName);


}
