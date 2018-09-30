package ru.nchernetsov.domain.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    @DBRef
    private List<Author> authors = new ArrayList<>();

    @DBRef
    private List<Genre> genres = new ArrayList<>();

    @DBRef
    private List<Comment> comments = new ArrayList<>();

    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    public Book(String title) {
        this();
        this.title = title;
    }

    public Book(String title, List<Author> authors, List<Genre> genres) {
        this();
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

    public List<String> getCommentsIds() {
        return comments.stream().map(Comment::getId).collect(Collectors.toList());
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void removeComment(String commentText) {
        for (Comment comment : comments) {
            if (comment.getComment().equals(commentText)) {
                comments.remove(comment);
                break;
            }
        }
    }

    public void deleteAuthorByName(String name) {
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                authors.remove(author);
                break;
            }
        }
    }

    public void deleteGenreByName(String name) {
        for (Genre genre : genres) {
            if (genre.getName().equals(name)) {
                genres.remove(genre);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Book{" +
            "title='" + title + '\'' +
            '}';
    }

}
