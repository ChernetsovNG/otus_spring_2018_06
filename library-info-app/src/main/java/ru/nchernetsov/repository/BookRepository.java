package ru.nchernetsov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nchernetsov.domain.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    void deleteByTitle(String title);
}
