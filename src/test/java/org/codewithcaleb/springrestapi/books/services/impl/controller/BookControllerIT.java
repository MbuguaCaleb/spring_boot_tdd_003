package org.codewithcaleb.springrestapi.books.services.impl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.codewithcaleb.springrestapi.TestData;
import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional //Will not persist the records
public class BookControllerIT {

    //Mock Mvc Helps me in making integration tests
    //Example calling my controller methods
    //When i write my integration tests i am testing my requests and responses.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreated() throws Exception {
        final Book book = TestData.testBook();

        //creating a json book for making the request
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(put("/books/" + book.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()));

    }

    @Test
    public void testThatRetrieveBooksReturns404WhenBookNotFound() throws Exception {
        mockMvc.perform(get("/books/12345678")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveBookReturns200AndBookWhenBookExists() throws Exception {
        //i want to create a Book then retrieve it
        final Book book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(get("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void testThatRetrieveListBookReturnsEmptyListHttp200WhenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    public void testThatRetrieveListBookReturnsHttp200AndBookListWhenBookExists() throws Exception {
        //i want to create a Book then retrieve it
        final Book book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(book.getTitle()));

    }

    @Test
    public void testThatUpdateBookReturnsHttp404WhenBookIsbnDoesNotExist() throws Exception {
        String invalidIsbn = "7686786";

        //creating a json book for making the request
        Book book = TestData.testBook();

        //Convert book Object into JSON
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(put("/books" + invalidIsbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateBookReturnsHttp200AndBookIsbnExist() throws Exception {

        Book book = TestData.testBook();
        bookService.create(book);

        //Convert book Object into JSON
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(put("/books/update/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
