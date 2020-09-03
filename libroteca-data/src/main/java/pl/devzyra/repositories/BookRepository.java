package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.BookEntity;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {
}
