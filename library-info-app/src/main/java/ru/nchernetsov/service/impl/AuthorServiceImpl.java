package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.service.AuthorService;

import java.util.ArrayList;
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
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findOne(String id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.orElse(null);
    }

    @Override
    public Author createOrUpdateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> createOrUpdateAuthorList(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }

    @Override
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
    public Author getAuthorByName(String name) {
        Optional<Author> authorOptional = authorRepository.findByName(name);
        return authorOptional.orElse(null);
    }
}
