package ru.nchernetsov.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.GenreRepository;
import ru.nchernetsov.service.GenreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    public GenreServiceImpl(BookRepository bookRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "genres", fallbackMethod = "findAllFallback")
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "genres", fallbackMethod = "findOneFallback")
    public Genre findOne(String id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        return genreOptional.orElse(null);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "genres", fallbackMethod = "createOrUpdateGenreFallback")
    public Genre createOrUpdateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "genres", fallbackMethod = "createOrUpdateGenreListFallback")
    public List<Genre> createOrUpdateGenreList(List<Genre> genres) {
        return genreRepository.saveAll(genres);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "genres", fallbackMethod = "deleteGenreByIdFallback")
    public void deleteGenreById(String id) {
        Genre genre = findOne(id);

        List<String> genreBookIds = genre.getBookIds();
        Iterable<Book> genreBooks = bookRepository.findAllById(genreBookIds);

        for (Book book : genreBooks) {
            book.deleteGenreByName(genre.getName());
        }

        genreRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "genres", fallbackMethod = "getGenreBooksFallback")
    public List<Book> getGenreBooks(String genreId) {
        Genre genre = findOne(genreId);

        List<Book> books = new ArrayList<>();
        if (genre != null) {  // если это не создание нового жанра
            List<String> genreBookIds = genre.getBookIds();
            Iterable<Book> genreBooks = bookRepository.findAllById(genreBookIds);
            for (Book book : genreBooks) {
                books.add(book);
            }
        }

        return books;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "genres", fallbackMethod = "getGenreByNameFallback")
    public Genre getGenreByName(String name) {
        Optional<Genre> genreOptional = genreRepository.findByName(name);
        return genreOptional.orElse(null);
    }

    // Fallback section

    public List<Genre> findAllFallback() {
        return Collections.emptyList();
    }

    public Genre findOneFallback(String id) {
        return null;
    }

    public Genre createOrUpdateGenreFallback(Genre genre) {
        return null;
    }

    public List<Genre> createOrUpdateGenreListFallback(List<Genre> genres) {
        return Collections.emptyList();
    }

    public void deleteGenreByIdFallback(String id) {
    }

    public List<Book> getGenreBooksFallback(String genreId) {
        return Collections.emptyList();
    }

    public Genre getGenreByNameFallback(String name) {
        return null;
    }
}
