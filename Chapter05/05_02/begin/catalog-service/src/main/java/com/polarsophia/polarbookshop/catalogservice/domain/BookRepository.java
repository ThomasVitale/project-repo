package com.polarsophia.polarbookshop.catalogservice.domain;

import java.util.Collection;
import java.util.Optional;

public interface BookRepository {
    Collection<Book> findAllOrderByTitle();
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    Book save(Book toBookEntity);
    Book delete(Book book);
    Book update(String isbn, Book book);
}
