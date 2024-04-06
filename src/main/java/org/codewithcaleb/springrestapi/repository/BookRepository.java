package org.codewithcaleb.springrestapi.repository;

import org.codewithcaleb.springrestapi.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
}
