package ru.nchernetsov.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String comment;

    private LocalDateTime timestamp;

    private String bookId;

    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    public Comment(String id, String comment) {
        this.id = id;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getBook() {
        return bookId;
    }

    public void setBook(Book book) {
        this.bookId = book.getId();
    }
}
