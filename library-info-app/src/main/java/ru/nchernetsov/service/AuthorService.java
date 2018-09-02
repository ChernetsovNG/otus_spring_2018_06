package ru.nchernetsov.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;

import java.util.List;

public interface AuthorService {

    Flux<Author> findAll();

    Mono<Author> findOne(String id);

    Mono<Author> createOrUpdateAuthor(Author author);

    Flux<Author> createOrUpdateAuthorList(List<Author> authors);

    Mono<Void> deleteAuthorById(String id);

    Flux<Book> getAuthorBooks(String authorId);

    Mono<Author> getAuthorByName(String name);
}
