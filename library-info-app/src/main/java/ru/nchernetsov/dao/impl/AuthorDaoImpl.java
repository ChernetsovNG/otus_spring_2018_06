package ru.nchernetsov.dao.impl;

import org.springframework.stereotype.Repository;
import ru.nchernetsov.dao.AuthorDao;
import ru.nchernetsov.domain.Author;

import java.util.List;

@Repository
public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author getById(long id) {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public void addAuthor(Author author) {

    }

    @Override
    public void deleteAuthor(Author author) {

    }

    /*private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getById(long id) {
        final Map<String, Object> param = Collections.singletonMap("id", id);
        return jdbc.queryForObject("SELECT * FROM AUTHORS WHERE ID = :id", param, (rs, rowNum) ->
                new Author(rs.getInt("id"), rs.getString("name")));
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * FROM AUTHORS", (rs, rowNum) ->
                new Author(rs.getInt("id"), rs.getString("name")));
    }

    @Override
    public void addAuthor(Author author) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", author.getId());
        params.put("name", author.getName());
        jdbc.update("INSERT INTO AUTHORS (ID, NAME) values (:id, :name)", params);
    }

    @Override
    public void deleteAuthor(Author author) {
        final Map<String, Object> param = Collections.singletonMap("id", author.getId());
        jdbc.update("DELETE FROM BOOKS_AUTHORS WHERE AUTHOR_ID = :id", param);
        jdbc.update("DELETE FROM AUTHORS WHERE ID = :id", param);
    }
*/
}
