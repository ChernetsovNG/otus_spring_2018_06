package ru.nchernetsov.dao;

import ru.nchernetsov.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre getById(long id);

    List<Genre> getAll();

    public void addGenre(Genre genre);

    public void deleteGenre(Genre genre);

}
