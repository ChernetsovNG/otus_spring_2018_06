package ru.nchernetsov.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.service.AuthorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "authors", fallbackMethod = "findAllFallback")
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "authors", fallbackMethod = "findOneFallback")
    public Author findOne(String id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.orElse(null);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "authors", fallbackMethod = "createOrUpdateAuthorFallback")
    public Author createOrUpdateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "authors", fallbackMethod = "createOrUpdateAuthorListFallback")
    public List<Author> createOrUpdateAuthorList(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "authors", fallbackMethod = "deleteAuthorByIdFallback")
    public Author deleteAuthorById(String id) {
        Author author = findOne(id);

        List<String> authorBookIds = author.getBookIds();
        Iterable<Book> authorBooks = bookRepository.findAllById(authorBookIds);

        for (Book book : authorBooks) {
            book.deleteAuthorByName(author.getName());
        }

        authorRepository.deleteById(id);

        return author;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "authors", fallbackMethod = "getAuthorBooksFallback")
    public List<Book> getAuthorBooks(String authorId) {
        Author author = findOne(authorId);

        List<Book> books = new ArrayList<>();
        if (author != null) {  // если это не создание нового автора
            List<String> authorBookIds = author.getBookIds();
            Iterable<Book> authorBooks = bookRepository.findAllById(authorBookIds);
            for (Book book : authorBooks) {
                books.add(book);
            }
        }

        return books;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "authors", fallbackMethod = "getAuthorByNameFallback")
    public Author getAuthorByName(String name) {
        Optional<Author> authorOptional = authorRepository.findByName(name);
        return authorOptional.orElse(null);
    }

    // Fallback section

    public List<Author> findAllFallback() {
        return Collections.emptyList();
    }

    public Author findOneFallback(String id) {
        return null;
    }

    public Author createOrUpdateAuthorFallback(Author author) {
        return null;
    }

    public List<Author> createOrUpdateAuthorListFallback(List<Author> authors) {
        return Collections.emptyList();
    }

    public Author deleteAuthorByIdFallback(String id) {
        return null;
    }

    public List<Book> getAuthorBooksFallback(String authorId) {
        return Collections.emptyList();
    }

    public Author getAuthorByNameFallback(String name) {
        return null;
    }
}
