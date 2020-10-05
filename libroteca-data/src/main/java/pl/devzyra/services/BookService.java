package pl.devzyra.services;

import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;

import java.util.List;

public interface BookService {

    BookRest createBook(BookRequestModel bookRequest) throws BookServiceException;

    BookRest getBook(Long bookId) throws BookServiceException;

    void deleteBook(Long bookId) throws UserServiceException;

    List<BookEntity> findAll(int page, int limit);

    BookEntity findByBookId(Long bookId);

    List<BookRest> findBooksByTitle(String title);

    List<BookRest> findBooksByAuthor(String author);

}
