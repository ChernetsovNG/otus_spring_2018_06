package ru.nchernetsov.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.mongodb.Author;

import java.util.Optional;

public interface MongoDBAuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByName(String name);

    void deleteByName(String name);
}
