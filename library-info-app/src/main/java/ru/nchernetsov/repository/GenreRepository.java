package ru.nchernetsov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);

    void deleteByName(String name);
}
