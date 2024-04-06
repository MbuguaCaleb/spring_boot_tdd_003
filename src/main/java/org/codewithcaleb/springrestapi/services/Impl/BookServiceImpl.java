package org.codewithcaleb.springrestapi.services.Impl;

import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.domain.BookEntity;
import org.codewithcaleb.springrestapi.repository.BookRepository;
import org.codewithcaleb.springrestapi.services.BookService;
import org.springframework.stereotype.Service;

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
