package org.codewithcaleb.springrestapi.controller;

import lombok.RequiredArgsConstructor;
import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn){
        Optional<Book> foundBook = bookService.findById(isbn);

        //the map methods predicate takes effect if the book exists
        return foundBook.map(book-> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks(){
        return new ResponseEntity<>(bookService.listBooks(),HttpStatus.OK);
    }

    @PutMapping(path = "/books/update/{isbn}")
    public ResponseEntity<Optional<Book>> updateBook(@RequestBody final Book book,
                                           @PathVariable final String isbn){

        Optional<Book> updatedBook = bookService.updateBook(book, isbn);

        return updatedBook.map(bookItem-> new ResponseEntity<>(updatedBook, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


}
