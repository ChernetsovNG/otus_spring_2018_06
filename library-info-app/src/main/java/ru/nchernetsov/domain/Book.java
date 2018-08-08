package ru.nchernetsov.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private List<Author> authors;

    private List<Genre> genres;

    private List<Comment> comments = new ArrayList<>();

    public Book() {
    }

    public Book(String title, List<Author> authors, List<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return Collections.unmodifiableList(genres);
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
