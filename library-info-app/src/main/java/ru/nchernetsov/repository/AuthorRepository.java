package ru.nchernetsov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nchernetsov.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    void deleteByName(String name);
}
