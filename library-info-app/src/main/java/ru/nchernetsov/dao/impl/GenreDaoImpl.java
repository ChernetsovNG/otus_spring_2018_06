package ru.nchernetsov.dao.impl;

import org.springframework.stereotype.Repository;
import ru.nchernetsov.dao.GenreDao;
import ru.nchernetsov.domain.Genre;

import java.util.List;

@Repository
public class GenreDaoImpl implements GenreDao {
    @Override
    public Genre getById(long id) {
        return null;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public void addGenre(Genre genre) {

    }

    @Override
    public void deleteGenre(Genre genre) {

    }

    /*private final NamedParameterJdbcOperations jdbc;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(long id) {
        final Map<String, Object> param = Collections.singletonMap("id", id);
        return jdbc.queryForObject("SELECT * FROM GENRES WHERE ID = :id", param, (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name")));
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT * FROM GENRES", (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name")));
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
        final Map<String, Object> param = Collections.singletonMap("id", genre.getId());
        jdbc.update("DELETE FROM BOOKS_GENRES WHERE GENRE_ID = :id", param);
        jdbc.update("DELETE FROM GENRES WHERE ID = :id", param);
    }*/

}
