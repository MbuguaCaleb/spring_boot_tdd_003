package org.codewithcaleb.springrestapi;


import lombok.NoArgsConstructor;
import org.codewithcaleb.springrestapi.domain.Book;
import org.codewithcaleb.springrestapi.domain.BookEntity;

@NoArgsConstructor
public final class TestData {

    public static Book testBook(){
        return Book.builder()
                .isbn("1234567")
                .title("New Christian Birth")
                .author("David Pawson")
                .build();
    }

    public static BookEntity testBookEntity(){
        return BookEntity.builder()
                .isbn("1234567")
                .title("New Christian Birth")
                .author("David Pawson")
                .build();
    }



}
