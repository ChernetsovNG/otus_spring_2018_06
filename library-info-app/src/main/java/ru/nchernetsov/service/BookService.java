package ru.nchernetsov.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;

public interface BookService {

    Flux<Book> findAll();

    Mono<Book> findOne(String id);

    Mono<Book> createOrUpdateBook(Book book);

    Book deleteBookById(String id);
}
