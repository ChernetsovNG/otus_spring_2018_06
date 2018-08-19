package ru.nchernetsov.service;

import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findOne(String id);

    Author createOrUpdateAuthor(Author author);

    List<Author> createOrUpdateAuthorList(List<Author> authors);

    void deleteAuthorById(String id);

    List<Book> getAuthorBooks(String authorId);

    Author getAuthorByName(String name);
}
