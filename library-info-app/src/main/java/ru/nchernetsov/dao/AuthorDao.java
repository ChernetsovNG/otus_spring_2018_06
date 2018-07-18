package ru.nchernetsov.dao;

import ru.nchernetsov.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(long id);

    List<Author> getAll();

    void addAuthor(Author author);

    void deleteAuthor(Author author);

}
