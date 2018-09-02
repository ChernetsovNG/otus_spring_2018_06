package ru.nchernetsov.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.List;

public interface GenreService {

    Flux<Genre> findAll();

    Mono<Genre> findOne(String id);

    Mono<Genre> createOrUpdateGenre(Genre genre);

    Flux<Genre> createOrUpdateGenreList(List<Genre> genres);

    Mono<Void> deleteGenreById(String id);

    Flux<Book> getGenreBooks(String genreId);

    Mono<Genre> getGenreByName(String name);
}
