package ru.nchernetsov.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.nchernetsov.dao.AuthorDao;
import ru.nchernetsov.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getById(long id) {
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", id);
        return jdbc.queryForObject("SELECT * FROM AUTHORS WHERE ID = :id", param, new AuthorRowMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * FROM AUTHORS", new AuthorRowMapper());
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
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", author.getId());
        jdbc.update("DELETE FROM BOOKS_AUTHORS WHERE AUTHOR_ID = :id", param);
        jdbc.update("DELETE FROM AUTHORS WHERE ID = :id", param);
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }

}
