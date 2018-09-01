package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.GenreRepository;
import ru.nchernetsov.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    public GenreServiceImpl(BookRepository bookRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Flux<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> findOne(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public Mono<Genre> createOrUpdateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Flux<Genre> createOrUpdateGenreList(List<Genre> genres) {
        return genreRepository.saveAll(genres);
    }

    @Override
    public Mono<Void> deleteGenreById(String id) {
        Mono<Genre> genreMono = findOne(id);

        genreMono.subscribe(genre -> {
            Flux<Book> genreBooks = bookRepository.findAllById(genre.getBookIds());
            genreBooks.subscribe(book -> book.deleteGenreByName(genre.getName()));
        });

        return genreRepository.deleteById(id);
    }

    @Override
    public Flux<Book> getGenreBooks(String genreId) {
        Mono<Genre> genreMono = findOne(genreId);
        return genreMono.map(genre ->
            bookRepository.findAllById(genre.getBookIds()))
            .flatMapMany(Flux::next);
    }

    @Override
    public Mono<Genre> getGenreByName(String name) {
        return genreRepository.findByName(name);
    }
}
