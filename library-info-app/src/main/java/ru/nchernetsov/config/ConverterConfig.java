package ru.nchernetsov.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.nchernetsov.service.AuthorService;
import ru.nchernetsov.service.GenreService;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    private final AuthorService authorService;

    private final GenreService genreService;

    public ConverterConfig(AuthorService authorService, GenreService genreService) {
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new NameToAuthorConverter(authorService));
        registry.addConverter(new NameToGenreConverter(genreService));
    }

}
