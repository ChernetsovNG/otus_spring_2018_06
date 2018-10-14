package ru.nchernetsov.domain;

import java.util.List;

public class AuthorWithBooks {

    private final Author author;

    private final List<Book> books;

    public AuthorWithBooks(Author author, List<Book> books) {
        this.author = author;
        this.books = books;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Book> getBooks() {
        return books;
    }
}
