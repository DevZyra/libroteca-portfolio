package pl.devzyra.services;

import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;

import java.util.List;

public interface BookService {

    BookRest createBook(BookRequestModel bookRequest);

    void deleteBook(Long bookId);

    List<BookEntity> findAll(int page , int limit);

}
