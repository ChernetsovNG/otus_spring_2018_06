package ru.nchernetsov.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
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
import java.util.stream.Collectors;

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
        return jdbc.query("SELECT * FROM BOOKS", new BookRowMapper());
    }

    @Override
    public void addBook(Book book) {

    }

    @Override
    public void deleteBook(Book book) {

    }

    @Override
    public void addAuthorToBook(Book book, Author author) {

    }

    @Override
    public void addGenreToBook(Book book, Genre genre) {

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
