package ru.nchernetsov;

import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

public final class Utils {

    private Utils() {
    }

    public static List<String> getBookTitles(List<Book> books) {
        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    public static List<String> getBookIds(List<Book> books) {
        return books.stream().map(Book::getBookId).collect(Collectors.toList());
    }

    public static List<String> getGenreNames(List<Genre> genres) {
        return genres.stream().map(Genre::getName).collect(Collectors.toList());
    }

    public static List<String> getAuthorNames(List<Author> authors) {
        return authors.stream().map(Author::getName).collect(Collectors.toList());
    }
}
