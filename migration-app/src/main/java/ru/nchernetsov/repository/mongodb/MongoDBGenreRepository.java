package ru.nchernetsov.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.mongodb.Genre;

public interface MongoDBGenreRepository extends MongoRepository<Genre, String> {
    boolean existsByName(String name);

    Genre findByName(String name);
}
