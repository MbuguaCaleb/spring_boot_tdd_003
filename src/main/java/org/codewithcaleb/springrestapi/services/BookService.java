package org.codewithcaleb.springrestapi.services;

import org.codewithcaleb.springrestapi.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

   Book create(Book book);

   Optional<Book> findById(String isbn);

   List<Book> listBooks();

   Optional<Book> updateBook(Book book, String isbn);

}
