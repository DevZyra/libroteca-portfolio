package pl.devzyra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.model.entities.BookEntity;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {


    BookEntity findByTitle(String title);

    Optional<BookEntity> findById(Long bookId);


}
