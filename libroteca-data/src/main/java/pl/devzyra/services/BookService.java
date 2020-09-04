package pl.devzyra.services;

import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;

public interface BookService {

    BookRest createBook(BookRequestModel bookRequest);

    void deleteBook(Long bookId);

}
