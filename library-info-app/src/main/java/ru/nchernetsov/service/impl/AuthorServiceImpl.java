package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> findOne(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> createOrUpdateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Flux<Author> createOrUpdateAuthorList(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }

    @Override
    public Mono<Author> deleteAuthorById(String id) {
        Mono<Author> authorMono = findOne(id);

        Author author = authorMono.block();

        if (author != null) {
            List<String> authorBookIds = author.getBookIds();
            Flux<Book> authorBooks = bookRepository.findAllById(authorBookIds);

            authorBooks.subscribe(book -> book.deleteAuthorByName(author.getName()));

            authorRepository.deleteById(id).subscribe();
        }

        return authorMono;
    }

    @Override
    public List<Book> getAuthorBooks(String authorId) {
        Mono<Author> authorMono = findOne(authorId);

        Author author = authorMono.block();

        List<Book> books = new ArrayList<>();

        if (author != null) {
            List<String> authorBookIds = author.getBookIds();
            Flux<Book> authorBooks = bookRepository.findAllById(authorBookIds);
            authorBooks.subscribe(books::add);
        }

        return books;
    }

    @Override
    public Mono<Author> getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }
}
