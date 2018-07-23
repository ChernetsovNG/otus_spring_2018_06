package ru.nchernetsov.repository;

import ru.nchernetsov.domain.Genre;

import java.util.List;

public interface GenreRepository {

    Genre getById(long id);

    List<Genre> getAll();

    void insert(Genre genre);

    Genre getByName(String name);

    void removeByName(String name);
}
