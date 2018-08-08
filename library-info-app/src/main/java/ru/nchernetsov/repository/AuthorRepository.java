package ru.nchernetsov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, Long> {
    Optional<Author> findByName(String name);

    void deleteByName(String name);
}
