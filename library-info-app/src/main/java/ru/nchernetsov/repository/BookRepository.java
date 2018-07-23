package ru.nchernetsov.repository;

import ru.nchernetsov.domain.Book;

import java.util.List;

public interface BookRepository {

    Book getById(long id);

    List<Book> getAll();

    void insert(Book book);

    void update(Book book);

    Book getByTitle(String title);

    void removeByTitle(String title);

}
