package org.codewithcaleb.springrestapi.controller;

import lombok.RequiredArgsConstructor;
import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createBook(@RequestBody final Book book,
                                           @PathVariable final String isbn){

        book.setIsbn(isbn);
        final Book savedBook = bookService.create(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);

    }
}
