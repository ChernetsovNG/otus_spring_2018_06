package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.service.GenreService;

import java.util.List;
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
    public List<Genre> getGenres() {
        return genreService.findAll();
    }

    @PostMapping(value = "/genres")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        genre.setId(UUID.randomUUID().toString());

        List<Book> genreBooks = genreService.getGenreBooks(genre.getId());
        genre.setBookIds(genreBooks);

        Genre createdGenre = genreService.createOrUpdateGenre(genre);

        return ResponseEntity.ok(createdGenre);
    }

    @PutMapping(value = "/genres")
    public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
        List<Book> genreBooks = genreService.getGenreBooks(genre.getId());
        genre.setBookIds(genreBooks);

        Genre updatedGenre = genreService.createOrUpdateGenre(genre);

        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping(value = "/genres/{genreId}")
    public ResponseEntity<?> deleteGenre(@PathVariable(name = "genreId") String genreId) {
        genreService.deleteGenreById(genreId);
        return ResponseEntity.ok().build();
    }

}
