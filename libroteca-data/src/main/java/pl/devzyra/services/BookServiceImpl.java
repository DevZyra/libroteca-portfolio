package pl.devzyra.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.entities.AuthorEntity;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.repositories.AuthorRepository;
import pl.devzyra.repositories.BookRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.devzyra.exceptions.ErrorMessages.NO_RECORD_FOUND;
import static pl.devzyra.exceptions.ErrorMessages.RECORD_ALREADY_EXISTS;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public BookRest createBook(BookRequestModel bookRequest) {

        if (bookRepository.findByTitle(bookRequest.getTitle()) != null) {
            throw new BookServiceException(RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookRequest.getTitle());
        bookEntity.setIsbn(bookRequest.getIsbn());

        Set<AuthorEntity> authorEntities = bookRequest.getAuthors().stream().map(x -> modelMapper.map(x, AuthorEntity.class)).collect(Collectors.toSet());
        authorRepository.saveAll(authorEntities);

        bookEntity.getAuthors().addAll(authorEntities);
        bookRepository.save(bookEntity);

        BookRest returnVal = modelMapper.map(bookEntity, BookRest.class);


        return returnVal;
    }

    @Override
    public void deleteBook(Long bookId) {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookId);

        if (bookEntity.isEmpty()) {
            throw new UserServiceException(NO_RECORD_FOUND.getErrorMessage());
        }

        bookRepository.delete(bookEntity.get());
    }
}
