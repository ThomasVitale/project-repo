package com.polarsophia.polarbookshop.catalogservice.web;

import com.polarsophia.polarbookshop.catalogservice.domain.Book;
import com.polarsophia.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarsophia.polarbookshop.catalogservice.domain.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Collection<Book> get() {
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return bookService.viewBookDetails(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    @PostMapping
    public Book post(@Valid @RequestBody Book book) {
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}")
    public Book delete(@PathVariable String isbn) {
        return bookService.removeBookFromCatalog(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    @PutMapping
    public Book put(@Valid @RequestBody Book book) {
        return bookService.editBookDetails(book);
    }
}
