package ru.nchernetsov.dao;

import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.List;

public interface BookDao {

    Book getById(long id);

    List<Book> getAll();

    public void addBook(Book book);

    public void deleteBook(Book book);

    public void addAuthorToBook(Book book, Author author);

    public void addGenreToBook(Book book, Genre genre);

}
