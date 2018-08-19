package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.service.GenreService;

import java.util.List;

@Controller
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String getGenres(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping(value = {"/genres/edit", "/genres/edit/{genreId}"})
    public String createOrEditGenreForm(Model model, @PathVariable(required = false, name = "genreId") String genreId) {
        if (genreId != null) {
            model.addAttribute("genre", genreService.findOne(genreId));
        } else {
            model.addAttribute("genre", new Genre());
        }
        return "editGenre";
    }

    @PostMapping(value = "/genres/edit")
    public String editGenre(Model model, Genre genre) {
        List<Book> genreBooks = genreService.getGenreBooks(genre.getId());
        genre.setBookIds(genreBooks);

        genreService.createOrUpdateGenre(genre);
        model.addAttribute("genres", genreService.findAll());

        return "redirect:/genres";
    }

    @GetMapping(value = "/genres/delete/{genreId}")
    public String deleteGenre(Model model, @PathVariable(name = "genreId") String genreId) {
        genreService.deleteGenreById(genreId);
        model.addAttribute("genres", genreService.findAll());
        return "redirect:/genres";
    }

}
