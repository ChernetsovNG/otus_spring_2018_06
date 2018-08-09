package ru.nchernetsov.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    private String name;

    private List<String> bookIds = new ArrayList<>();

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String id, String name) {
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


}
