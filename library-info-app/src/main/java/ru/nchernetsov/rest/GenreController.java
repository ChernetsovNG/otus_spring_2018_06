package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.service.GenreService;

import java.util.List;

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
    public ResponseEntity<?> editGenre(@RequestBody Genre genre) {
        List<Book> genreBooks = genreService.getGenreBooks(genre.getId());
        genre.setBookIds(genreBooks);

        genreService.createOrUpdateGenre(genre);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/genres/{genreId}")
    public ResponseEntity<?> deleteGenre(@PathVariable(name = "genreId") String genreId) {
        genreService.deleteGenreById(genreId);
        return ResponseEntity.ok().build();
    }

}
