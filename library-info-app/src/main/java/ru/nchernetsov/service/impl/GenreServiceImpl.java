package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.GenreRepository;
import ru.nchernetsov.service.GenreService;

import java.util.ArrayList;
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
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findOne(String id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        return genreOptional.orElse(null);
    }

    @Override
    public Genre createOrUpdateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> createOrUpdateGenreList(List<Genre> genres) {
        return genreRepository.saveAll(genres);
    }

    @Override
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
    public Genre getGenreByName(String name) {
        Optional<Genre> genreOptional = genreRepository.findByName(name);
        return genreOptional.orElse(null);
    }
}
