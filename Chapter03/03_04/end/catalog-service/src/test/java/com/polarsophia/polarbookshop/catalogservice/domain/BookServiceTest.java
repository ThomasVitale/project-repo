package com.polarsophia.polarbookshop.catalogservice.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookServiceTest {
    private static BookService bookService;

    @BeforeAll
    static void setUp() {
        bookService = new BookService();
    }

    @Test
    void whenBookToCreateAlreadyExistsThenThrows() {
        String bookIsbn = "1234561232";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(2000));
        bookService.addBookToCatalog(bookToCreate);
        assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
                .hasMessage("A book with ISBN " + bookIsbn + " already exists.");
    }

    @Test
    void whenBookToUpdateNotFoundThenThrows() {
        String bookIsbn = "1234561234";
        Book bookToUpdate = new Book(bookIsbn, "Title", "Author", Year.of(2000));
        assertThatThrownBy(() -> bookService.editBookDetails(bookToUpdate))
                .hasMessage("The book with ISBN " + bookIsbn + " was not found.");
    }
}
