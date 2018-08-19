package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.repository.GenreRepository;
import ru.nchernetsov.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findOne(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    @Override
    public Book createOrUpdateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }
}
