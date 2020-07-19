package com.polarsophia.polarbookshop.catalogservice.domain;

import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookService {
    private static final Map<String,Book> books = new ConcurrentHashMap<>();

    static {
        Book book = new Book("1234567891", "Northern Light", "Lyra Silvertongue", Year.of(2001));
        books.put(book.isbn, book);
    }

    public Collection<Book> viewBookList() {
        return books.values();
    }

    public Optional<Book> viewBookDetails(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (viewBookDetails(book.isbn).isPresent()) {
            throw new BookAlreadyExistsException(book.isbn);
        }
        books.put(book.isbn, book);
        return book;
    }

    public Optional<Book> removeBookFromCatalog(String isbn) {
        return Optional.ofNullable(books.remove(isbn));
    }

    public Book editBookDetails(Book book) {
        if (viewBookDetails(book.isbn).isEmpty()) {
            throw new BookNotFoundException(book.isbn);
        }
        books.put(book.isbn, book);
        return book;
    }
}
