package ru.nchernetsov.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findByTitle(String title);

    void deleteByTitle(String title);
}
