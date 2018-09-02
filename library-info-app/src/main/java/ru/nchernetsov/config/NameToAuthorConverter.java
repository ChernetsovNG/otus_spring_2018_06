package ru.nchernetsov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.service.AuthorService;

@Component
public class NameToAuthorConverter implements Converter<String, Author> {

    private final AuthorService authorService;

    @Autowired
    public NameToAuthorConverter(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public Author convert(String authorName) {
        return authorService.getAuthorByName(authorName);
    }
}
