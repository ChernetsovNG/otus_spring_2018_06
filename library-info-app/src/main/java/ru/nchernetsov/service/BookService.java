package ru.nchernetsov.service;

import ru.nchernetsov.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findOne(String id);

    Book createOrUpdateBook(Book book);

    void deleteBookById(String id);
}
