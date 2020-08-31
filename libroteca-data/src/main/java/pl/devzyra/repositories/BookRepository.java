package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.devzyra.model.entities.BookEntity;

public interface BookRepository extends CrudRepository<BookEntity,Long> {
}
