package pl.devzyra.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.devzyra.exceptions.BookServiceException;
import pl.devzyra.exceptions.UserServiceException;
import pl.devzyra.model.entities.AuthorEntity;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.request.AuthorRequestModel;
import pl.devzyra.model.request.BookRequestModel;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.repositories.AuthorRepository;
import pl.devzyra.repositories.BookRepository;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookServiceImplTest {


    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    ModelMapper modelMapper;

    BookEntity bookEntity;
    BookEntity bookEntity02;
    AuthorEntity authorEntity;
    BookRest bookRest;
    Page<BookEntity> page;
    BookRequestModel bookRequest;
    AuthorRequestModel authorRequest;
    static final String testIsbn = "978-2-12-345680-3";
    static final String testTitle = "Test book";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        authorRequest = new AuthorRequestModel();
        authorRequest.setFirstName("Author");
        authorRequest.setLastName("Testovsky");


        bookRequest = new BookRequestModel();
        bookRequest.setTitle(testTitle);
        bookRequest.setIsbn(testIsbn);
        bookRequest.setAuthors(Set.of(authorRequest));


        bookEntity = new BookEntity();
        bookEntity.setTitle(testTitle);
        bookEntity.setIsbn(testIsbn);

        bookEntity02 = new BookEntity();
        bookEntity02.setTitle("Test book 02");
        bookEntity02.setIsbn(testIsbn);


        authorEntity = new AuthorEntity();
        authorEntity.setFirstName("Author");
        authorEntity.setLastName("Testovsky");
        authorEntity.setBooks(Set.of(bookEntity));
        // bookEntity set author entity
        bookEntity.setAuthors(Set.of(authorEntity));

        bookEntity02.setAuthors(Set.of(authorEntity));

        bookRest = new BookRest();
        bookRest.setId(0L);
        bookRest.setTitle(testTitle);
        bookRest.setIsbn(testIsbn);
        bookRest.setAuthors(Set.of(authorEntity));
        // list of two books
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(bookEntity);
        bookEntityList.add(bookEntity02);
        // page that returns two books
        page = new PageImpl<>(bookEntityList);
    }

    @Test
    void createBookSuccessful() throws BookServiceException {
        Set<AuthorEntity> authors = new HashSet<>();
        authors.add(authorEntity);

        when(bookRepository.findByTitle(anyString())).thenReturn(null);
        when(authorRepository.saveAll(Set.of(authorEntity))).thenReturn(authors);

        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);
        when(modelMapper.map(authorRequest, AuthorEntity.class)).thenReturn(authorEntity);
        when(modelMapper.map(bookEntity, BookRest.class)).thenReturn(bookRest);

        BookRest book = bookService.createBook(bookRequest);

        assertNotNull(book.getTitle());
        assertNotNull(book);
        verify(bookRepository, times(1)).findByTitle(anyString());
        verify(authorRepository, times(1)).saveAll(anySet());
        verify(bookRepository, times(1)).save(any(BookEntity.class));


    }

    @Test
    void getById() throws BookServiceException {
        when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(bookEntity));
        when(modelMapper.map(bookEntity, BookRest.class)).thenReturn(bookRest);

        BookRest bookRest = bookService.getById(anyLong());

        assertNotNull(bookRest);
        verify(bookRepository, times(1)).findById(anyLong());
        assertEquals(testTitle, bookRest.getTitle());
    }

    @Test
    void getByIdThrowBookServiceException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<BookEntity> byId = bookRepository.findById(anyLong());

        assertThrows(BookServiceException.class, () -> {
            bookService.getById(anyLong());
        });

        assertEquals(Optional.empty(), byId);
    }


    @Test
    void deleteBook() throws UserServiceException {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bookEntity));

        Optional<BookEntity> bookOptional = bookRepository.findById(anyLong());

        bookService.deleteBook(0L);

        verify(bookRepository, times(1)).delete(any(BookEntity.class));
        assertNotNull(bookOptional);
    }

    @Test
    void findAll() {
        Pageable pageableRequest = PageRequest.of(1, 10);

        when(bookRepository.findAll(pageableRequest)).thenReturn(page);

        List<BookEntity> booksPage = bookService.findAll(1, 10);

        assertNotNull(booksPage);
        assertEquals(2, page.getTotalElements());
        verify(bookRepository, times(1)).findAll(pageableRequest);
    }

    @Test
    void findByBookId() throws BookServiceException {

        when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(bookEntity));

        BookEntity book = bookService.findByBookId(anyLong());

        assertNotNull(book);
        assertEquals(testTitle, book.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void findBooksByTitle() {
        List<BookEntity> booksStub = new ArrayList<>();
        booksStub.add(bookEntity);

        when(bookRepository.findAllByTitleContainingIgnoreCase(anyString())).thenReturn(booksStub);
        when(modelMapper.map(bookEntity, BookRest.class)).thenReturn(bookRest);

        List<BookRest> books = bookService.findBooksByTitle(anyString());

        assertNotNull(books);
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAllByTitleContainingIgnoreCase(anyString());

    }

    @Test
    void findBooksByAuthor() {

        List<BookEntity> booksStub = new ArrayList<>();
        booksStub.add(bookEntity);
        booksStub.add(bookEntity02);

        List<BookRest> booksRest = new ArrayList<>();
        booksRest.add(bookRest);
        Type listType = new TypeToken<List<BookRest>>() {
        }.getType();

        when(modelMapper.map(authorEntity.getBooks(), listType)).thenReturn(booksRest);
        when(authorRepository.findAllByAuthorFullName(anyString())).thenReturn(List.of(authorEntity));

        List<BookRest> booksByAuthor = bookService.findBooksByAuthor(anyString());

        assertNotNull(booksByAuthor);
        verify(authorRepository, times(1)).findAllByAuthorFullName(anyString());
        verify(modelMapper, times(1)).map(bookEntity, BookRest.class);
    }
}