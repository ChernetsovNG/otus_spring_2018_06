package ru.nchernetsov.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String comment;

    private LocalDateTime timestamp;

    private String bookId;

    public Comment() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
    }

    public Comment(String comment) {
        this();
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    public Comment(String id, String comment) {
        this();
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "comment='" + comment + '\'' +
            '}';
    }
}
