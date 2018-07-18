package ru.nchernetsov.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.nchernetsov.dao.AuthorDao;
import ru.nchernetsov.dao.BookDao;
import ru.nchernetsov.dao.GenreDao;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookDaoImpl(NamedParameterJdbcOperations jdbc, AuthorDao authorDao, GenreDao genreDao) {
        this.jdbc = jdbc;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> param = Collections.singletonMap("id", id);
        Book book = jdbc.queryForObject("SELECT * FROM BOOKS WHERE ID = :id", param, (rs, rowNum) ->
                new Book(rs.getInt("id"), rs.getString("title")));
        if (book != null) {
            book.addAuthors(getBookAuthors(id));
            book.addGenres(getBookGenres(id));
        }
        return book;
    }


    @Override
    public List<Book> getAll() {
        List<Book> books = jdbc.query("SELECT * FROM BOOKS", (rs, rowNum) ->
                new Book(rs.getInt("id"), rs.getString("title")));
        for (Book book : books) {
            book.addAuthors(getBookAuthors(book.getId()));
            book.addGenres(getBookGenres(book.getId()));
        }
        return books;
    }

    @Override
    public void addBook(Book book) {
        final Map<String, Object> params1 = new HashMap<>(2);
        params1.put("id", book.getId());
        params1.put("title", book.getTitle());
        jdbc.update("INSERT INTO BOOKS (ID, TITLE) values (:id, :title)", params1);
        // Заполняем связующие таблицы
        for (Author author : book.getAuthors()) {
            final Map<String, Object> params2 = new HashMap<>(2);
            params2.put("book_id", book.getId());
            params2.put("author_id", author.getId());
            jdbc.update("INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) values (:book_id, :author_id)", params2);
        }
        for (Genre genre : book.getGenres()) {
            final Map<String, Object> params3 = new HashMap<>(2);
            params3.put("book_id", book.getId());
            params3.put("genre_id", genre.getId());
            jdbc.update("INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID) values (:book_id, :genre_id)", params3);
        }
    }

    @Override
    public void deleteBook(Book book) {
        final Map<String, Object> param = Collections.singletonMap("id", book.getId());
        // очищаем связующие таблицы
        jdbc.update("DELETE FROM BOOKS_AUTHORS WHERE BOOK_ID = :id", param);
        jdbc.update("DELETE FROM BOOKS_GENRES WHERE BOOK_ID = :id", param);

        jdbc.update("DELETE FROM BOOKS WHERE ID = :id", param);
    }

    @Override
    public void addAuthorToBook(Book book, Author author) {
        book.addAuthor(author);
        final Map<String, Object> params = new HashMap<>(2);
        params.put("book_id", book.getId());
        params.put("author_id", author.getId());
        jdbc.update("INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) values (:book_id, :author_id)", params);
    }

    @Override
    public void addGenreToBook(Book book, Genre genre) {
        book.addGenre(genre);
        final Map<String, Object> params = new HashMap<>(2);
        params.put("book_id", book.getId());
        params.put("genre_id", genre.getId());
        jdbc.update("INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID) values (:book_id, :genre_id)", params);
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        final Map<String, Object> param = Collections.singletonMap("id", author.getId());
        List<Long> authorBooksIds = jdbc.query("SELECT BOOK_ID FROM BOOKS_AUTHORS WHERE AUTHOR_ID = :id", param,
                (rs, rowNum) -> rs.getLong("book_id"));
        return authorBooksIds.stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        final Map<String, Object> param = Collections.singletonMap("id", genre.getId());
        List<Long> genreBooksIds = jdbc.query("SELECT BOOK_ID FROM BOOKS_GENRES WHERE GENRE_ID = :id", param,
                (rs, rowNum) -> rs.getLong("book_id"));
        return genreBooksIds.stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    private List<Author> getBookAuthors(long id) {
        final Map<String, Object> param = Collections.singletonMap("id", id);
        List<Long> bookAuthorsIds = jdbc.query("SELECT AUTHOR_ID FROM BOOKS_AUTHORS WHERE BOOK_ID = :id", param,
                (rs, rowNum) -> rs.getLong("author_id"));
        return bookAuthorsIds.stream()
                .map(authorDao::getById)
                .collect(Collectors.toList());
    }

    private List<Genre> getBookGenres(long id) {
        final Map<String, Object> param = Collections.singletonMap("id", id);
        List<Long> booksGenresIds = jdbc.query("SELECT GENRE_ID FROM BOOKS_GENRES WHERE BOOK_ID = :id", param,
                (rs, rowNum) -> rs.getLong("genre_id"));
        return booksGenresIds.stream()
                .map(genreDao::getById)
                .collect(Collectors.toList());
    }

}
