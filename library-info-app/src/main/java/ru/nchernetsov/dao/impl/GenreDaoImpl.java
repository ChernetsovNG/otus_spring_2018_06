package ru.nchernetsov.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.nchernetsov.dao.GenreDao;
import ru.nchernetsov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(long id) {
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", id);
        return jdbc.queryForObject("SELECT * FROM GENRES WHERE ID = :id", param, new GenreRowMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT * FROM GENRES", new GenreRowMapper());
    }

    @Override
    public void addGenre(Genre genre) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        jdbc.update("INSERT INTO GENRES (ID, NAME) values (:id, :name)", params);
    }

    @Override
    public void deleteGenre(Genre genre) {
        final HashMap<String, Object> param = new HashMap<>(1);
        param.put("id", genre.getId());
        jdbc.update("DELETE FROM BOOKS_GENRES WHERE GENRE_ID = :id", param);
        jdbc.update("DELETE FROM GENRES WHERE ID = :id", param);
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }

}
