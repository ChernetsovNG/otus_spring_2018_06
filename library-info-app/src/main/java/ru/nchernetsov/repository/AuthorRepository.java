package ru.nchernetsov.repository;

import ru.nchernetsov.domain.Author;

import java.util.List;

public interface AuthorRepository {

    Author getById(long id);

    List<Author> getAll();

    void insert(Author author);

    Author getByName(String name);

    void removeByName(String name);

}
