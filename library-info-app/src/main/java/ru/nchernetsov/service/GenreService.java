package ru.nchernetsov.service;

import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre findOne(String id);

    Genre createOrUpdateGenre(Genre genre);

    List<Genre> createOrUpdateGenreList(List<Genre> genres);

    void deleteGenreById(String id);

    List<Book> getGenreBooks(String genreId);

    Genre getGenreByName(String name);
}
