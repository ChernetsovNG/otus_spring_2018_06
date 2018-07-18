package ru.nchernetsov.dao;

import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.List;

public interface BookDao {

    Book getById(long id);

    List<Book> getAll();

    void addBook(Book book);

    void deleteBook(Book book);

    void addAuthorToBook(Book book, Author author);

    void addGenreToBook(Book book, Genre genre);

    List<Book> getBooksByAuthor(Author author);

    List<Book> getBooksByGenre(Genre genre);

}
