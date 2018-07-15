package ru.nchernetsov.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Book {

    private long id;

    private String title;

    private List<Author> authors = new ArrayList<>();

    private List<Genre> genres = new ArrayList<>();

    public Book(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void addAuthors(List<Author> authors) {
        this.authors.addAll(authors);
    }

    public void addGenres(List<Genre> genres) {
        this.genres.addAll(genres);
    }
}
