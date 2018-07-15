package ru.nchernetsov.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.nchernetsov.dao.AuthorDao;
import ru.nchernetsov.dao.BookDao;
import ru.nchernetsov.dao.GenreDao;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", id);
        Book book = jdbc.queryForObject("SELECT * FROM BOOKS WHERE ID = :id", param, new BookRowMapper());
        if (book != null) {
            book.addAuthors(getBookAuthors(id));
            book.addGenres(getBookGenres(id));
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = jdbc.query("SELECT * FROM BOOKS", new BookRowMapper());
        for (Book book : books) {
            book.addAuthors(getBookAuthors(book.getId()));
            book.addGenres(getBookGenres(book.getId()));
        }
        return books;
    }

    @Override
    public void addBook(Book book) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        jdbc.update("INSERT INTO BOOKS (ID, TITLE) values (:id, :title)", params);
        // Заполняем связующие таблицы
        params.clear();
        for (Author author : book.getAuthors()) {
            params.put("book_id", book.getId());
            params.put("author_id", author.getId());
            jdbc.update("INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) values (:book_id, :author_id)", params);
            params.clear();
        }
        for (Genre genre : book.getGenres()) {
            params.put("book_id", book.getId());
            params.put("genre_id", genre.getId());
            jdbc.update("INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID) values (:book_id, :genre_id)", params);
            params.clear();
        }
    }

    @Override
    public void deleteBook(Book book) {
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", book.getId());
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
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", author.getId());
        List<Long> authorBooksIds = jdbc.query("SELECT * FROM BOOKS_AUTHORS WHERE AUTHOR_ID = :id", param,
            (rs, rowNum) -> rs.getLong("book_id"));
        return authorBooksIds.stream()
            .map(this::getById)
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", genre.getId());
        List<Long> genreBooksIds = jdbc.query("SELECT * FROM BOOKS_GENRES WHERE GENRE_ID = :id", param,
            (rs, rowNum) -> rs.getLong("book_id"));
        return genreBooksIds.stream()
            .map(this::getById)
            .collect(Collectors.toList());
    }

    private List<Author> getBookAuthors(long id) {
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", id);
        List<Long> bookAuthorsIds = jdbc.query("SELECT * FROM BOOKS_AUTHORS WHERE BOOK_ID = :id", param,
            (rs, rowNum) -> rs.getLong("author_id"));
        return bookAuthorsIds.stream()
            .map(authorDao::getById)
            .collect(Collectors.toList());
    }

    private List<Genre> getBookGenres(long id) {
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", id);
        List<Long> booksGenresIds = jdbc.query("SELECT * FROM BOOKS_GENRES WHERE BOOK_ID = :id", param,
            (rs, rowNum) -> rs.getLong("genre_id"));
        return booksGenresIds.stream()
            .map(genreDao::getById)
            .collect(Collectors.toList());
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            return new Book(id, title);
        }
    }

}
