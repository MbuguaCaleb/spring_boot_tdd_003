package org.codewithcaleb.springrestapi.services.Impl;

import org.codewithcaleb.springrestapi.TestData;
import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.domain.BookEntity;
import org.codewithcaleb.springrestapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    //I only care what this class does,thus i will inject everything external
    //i am only testing the behavior of the class
    @Mock
    private BookRepository bookRepository;

    // i will inject the mocked book repository into the Impl Test
    @InjectMocks//
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = TestData.testBook();

        //i cannot call method to convert Book to BookEntity
        //i unit test i just define my data
        final BookEntity bookEntity = TestData.testBookEntity();

        //Mocking what my repository will return
        //What i am concerned is the logic in the class
        //repositories are tested via integrations test
        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        //unit testing my method create
       final Book result = underTest.create(book);

       //unit tests i will have asserts
       assertEquals(book,result);

    }


    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook(){
        final String isbn ="1234567";

        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());
        Optional<Book> result = underTest.findById(isbn);

        assertEquals(Optional.empty(),result);

    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists(){
        final Book book = TestData.testBook();
        final BookEntity bookEntity = TestData.testBookEntity();

        when(bookRepository.findById(book.getIsbn())).thenReturn(Optional.of(bookEntity));

        Optional<Book> result = underTest.findById(book.getIsbn());

        assertEquals(Optional.of(book),result);
    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBookExists(){
        //remember I have not supplied any book inside myMock
        //this should return an empty list

        //method one
      // List<Book> result = underTest.listBooks();
      // assertEquals(0,result.size());

        //method two
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        List<Book> result = underTest.listBooks();
        assertEquals(0,result.size());

    }

    @Test
    public void testListBooksReturnsListWhenNoBookExists(){

        //method two
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        List<Book> result = underTest.listBooks();
        assertEquals(0,result.size());

    }


    @Test
    public void testListBooksReturnsListWhenBookExists(){

        BookEntity bookEntity = TestData.testBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));

        List<Book> result = underTest.listBooks();
        assertEquals(1,result.size());

    }

    @Test
    public void testUpdateBooksReturnsEmptyOptionalWhenIsbnDoesNotExist(){

        /*i need to provide or mock all the parameters that my method consumes or expect
        //The day I will go to change my code and the behavior of my code changes,
         then either my code is wrong or i update my tests*/

        String invalidIsbn = "123456737688";
        when(bookRepository.findById(invalidIsbn)).thenReturn(Optional.empty());

        Book bookToBeUpdated = TestData.testBook();

        Optional<Book> result = underTest.updateBook(bookToBeUpdated, invalidIsbn);

        assertEquals(Optional.empty(),result);
    }


}
