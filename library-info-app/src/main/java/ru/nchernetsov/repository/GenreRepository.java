package ru.nchernetsov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nchernetsov.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);

    void deleteByName(String name);
}
