package org.codewithcaleb.springrestapi.services.Impl;

import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.domain.BookEntity;
import org.codewithcaleb.springrestapi.repository.BookRepository;
import org.codewithcaleb.springrestapi.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    //When i mark bookRepository as final it cannot be reassigned once a class is instantiated
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }


    @Override
    public Optional<Book> findById(String isbn) {
        Optional<BookEntity> foundBook = bookRepository.findById(isbn);

        //functional pattern
        //if the bookEntity exists it will convert it to a book
        //if it does not exist, it will just return an optional.empty
        return foundBook.map(this::bookEntityToBook);
    }

    @Override
    public List<Book> listBooks() {
        List<BookEntity> foundBooks = bookRepository.findAll();

        //i am getting a predicate and i am just passing a predicate to a given method
        return foundBooks.stream().map(this::bookEntityToBook).toList();

    }

    @Override
    public Optional<Book> updateBook(Book book, String isbn) {
        Optional<BookEntity> findBook = bookRepository.findById(isbn);

        return findBook.map(bookEntity -> {
            bookEntity.setAuthor(book.getAuthor());
            bookEntity.setTitle(book.getTitle());
            bookRepository.save(bookEntity);
            return bookEntityToBook(bookEntity);
       });

    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor()).
                build();
    }

    private Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor()).build();
    }
}
