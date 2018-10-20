package ru.nchernetsov.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.mongodb.Author;

public interface MongoDBAuthorRepository extends MongoRepository<Author, String> {
    boolean existsByName(String name);

    Author findByName(String name);
}
