package org.codewithcaleb.springrestapi.books.services.impl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codewithcaleb.springrestapi.TestData;
import org.codewithcaleb.springrestapi.controller.BookController;
import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {

    //Mock Mvc Helps me in making integration tests
    //Example calling my controller methods
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testThatBookIsCreated() throws Exception {
        final Book book = TestData.testBook();

        //creating a json book response for asserting the correct response
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
}
