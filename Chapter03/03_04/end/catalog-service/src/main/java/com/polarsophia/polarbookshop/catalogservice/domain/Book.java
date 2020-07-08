package com.polarsophia.polarbookshop.catalogservice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.Year;

public class Book {
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^(97([89]))?\\d{9}(\\d|X)$", message = "The ISBN format must follow the standards ISBN-10 or ISBN-13.")
    public String isbn;
    @NotBlank(message = "The book title must be defined.")
    public String title;
    @NotBlank(message = "The book author must be defined.")
    public String author;
    @PastOrPresent(message = "The book cannot have been published in the future.")
    public Year publishDate;

    public Book(String isbn, String title, String author, Year publishDate) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Year getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Year publishDate) {
        this.publishDate = publishDate;
    }
}
