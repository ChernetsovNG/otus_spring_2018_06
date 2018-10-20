package ru.nchernetsov.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.mongodb.Book;

public interface MongoDBBookRepository extends MongoRepository<Book, String> {
}
