package ru.nchernetsov.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nchernetsov.domain.sql.Author;

import java.util.Optional;

public interface SqlAuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    void deleteByName(String name);
}
