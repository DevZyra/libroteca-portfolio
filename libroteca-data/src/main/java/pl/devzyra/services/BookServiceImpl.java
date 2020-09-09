package pl.devzyra.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.entities.AuthorEntity;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.repositories.AuthorRepository;
import pl.devzyra.repositories.BookRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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

    @Transactional
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
    public BookRest getBook(Long bookId) {

        Optional<BookEntity> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new BookServiceException(NO_RECORD_FOUND.getErrorMessage());
        }
        BookEntity bookEntity = book.get();

        return modelMapper.map(bookEntity, BookRest.class);
    }

    @Override
    public void deleteBook(Long bookId) {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookId);

        if (bookEntity.isEmpty()) {
            throw new UserServiceException(NO_RECORD_FOUND.getErrorMessage());
        }

        bookRepository.delete(bookEntity.get());
    }

    @Override
    public List<BookEntity> findAll(int page, int limit) {


        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<BookEntity> bookPage = bookRepository.findAll(pageableRequest);

        return bookPage.getContent();
    }

    @Override
    public List<BookRest> findBooksByTitle(String title) {
        List<BookRest> returnVal = new ArrayList<>();

        List<BookEntity> booksByTitle = bookRepository.findAllByTitleContainingIgnoreCase(title);

        if (booksByTitle.isEmpty()) {
            throw new BookServiceException(NO_RECORD_FOUND.getErrorMessage());
        }

        booksByTitle.forEach(b -> {
            BookRest bookRest = modelMapper.map(b, BookRest.class);
            returnVal.add(bookRest);
        });


        return returnVal;
    }

    @Override
    public List<BookRest> findBooksByAuthor(String author) {
        List<BookRest> returnVal = new ArrayList<>();

        Type listType = new TypeToken<List<BookRest>>() {
        }.getType();

        List<AuthorEntity> authorsFound = authorRepository.findAllByFirstNameOrLastNameContainingIgnoreCase(author, author);

        if (authorsFound.isEmpty()) {
            throw new BookServiceException(NO_RECORD_FOUND.getErrorMessage());
        }

        authorsFound.stream().forEach(a -> {
            List<BookRest> bookRest = modelMapper.map(a.getBooks(), listType);
            returnVal.addAll(bookRest);
        });

        return returnVal;
    }
}
