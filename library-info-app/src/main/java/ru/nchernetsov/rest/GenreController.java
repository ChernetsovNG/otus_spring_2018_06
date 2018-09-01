package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.service.GenreService;

import java.util.UUID;

@RestController
@CrossOrigin
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public Flux<Genre> getGenres() {
        return genreService.findAll();
    }

    @PostMapping(value = "/genres")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Genre> createGenre(@RequestBody Genre genre) {
        genre.setId(UUID.randomUUID().toString());

        Flux<Book> genreBooks = genreService.getGenreBooks(genre.getId());
        genreBooks.collectList().subscribe(genre::setBookIds);

        return genreService.createOrUpdateGenre(genre);
    }

    @PutMapping(value = "/genres")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Genre>> updateGenre(@RequestBody Genre genre) {
        Flux<Book> genreBooks = genreService.getGenreBooks(genre.getId());
        genreBooks.collectList().subscribe(genre::setBookIds);

        return genreService.createOrUpdateGenre(genre)
            .map(updatedGenre -> new ResponseEntity<>(updatedGenre, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/genres/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Void>> deleteGenre(@PathVariable(name = "genreId") String genreId) {
        return genreService.deleteGenreById(genreId)
            .then(Mono.just(new ResponseEntity<>(HttpStatus.OK)));
    }

}
