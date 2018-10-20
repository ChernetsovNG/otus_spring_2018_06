package ru.nchernetsov.domain.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private List<String> bookIds = new ArrayList<>();

    public Author() {
        this.id = UUID.randomUUID().toString();
    }

    public Author(String name) {
        this();
        this.name = name;
    }

    public Author(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBookIds() {
        return Collections.unmodifiableList(bookIds);
    }

    public void setBookIds(List<Book> books) {
        this.bookIds = books.stream().map(Book::getId).collect(Collectors.toList());
    }

    public void addBook(Book book) {
        if (!bookIds.contains(book.getId())) {
            bookIds.add(book.getId());
        }
    }

    @Override
    public String toString() {
        return "Author{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
