package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.service.AuthorService;

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
    public Mono<Void> deleteAuthorById(String id) {
        Mono<Author> authorMono = findOne(id);

        authorMono.subscribe(author -> {
            Flux<Book> authorBooks = bookRepository.findAllById(author.getBookIds());
            authorBooks.subscribe(book -> book.deleteAuthorByName(author.getName()));
        });

        return authorRepository.deleteById(id);
    }

    @Override
    public Flux<Book> getAuthorBooks(String authorId) {
        Mono<Author> authorMono = findOne(authorId);
        return authorMono.map(author -> bookRepository.findAllById(author.getBookIds()))
            .flatMapMany(Flux::next);
    }

    @Override
    public Mono<Author> getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }
}
