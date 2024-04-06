package org.codewithcaleb.springrestapi.books.services.impl;

import org.codewithcaleb.springrestapi.TestData;
import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.domain.BookEntity;
import org.codewithcaleb.springrestapi.repository.BookRepository;
import org.codewithcaleb.springrestapi.services.Impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
