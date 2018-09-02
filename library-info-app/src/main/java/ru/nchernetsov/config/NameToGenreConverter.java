package ru.nchernetsov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.service.GenreService;

@Component
public class NameToGenreConverter implements Converter<String, Genre> {

    private final GenreService genreService;

    @Autowired
    public NameToGenreConverter(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public Genre convert(String genreName) {
        return genreService.getGenreByName(genreName);
    }
}
