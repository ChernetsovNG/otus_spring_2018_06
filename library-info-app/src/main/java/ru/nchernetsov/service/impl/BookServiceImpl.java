package ru.nchernetsov.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "books", fallbackMethod = "findAllFallback")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "books", fallbackMethod = "findOneFallback")
    public Book findOne(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "books", fallbackMethod = "createOrUpdateBookFallback")
    public Book createOrUpdateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "books", fallbackMethod = "deleteBookByIdFallback")
    public Book deleteBookById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
            return bookOptional.get();
        } else {
            return null;
        }
    }

    // Fallback section

    public List<Book> findAllFallback() {
        return Collections.emptyList();
    }

    public Book findOneFallback(String id) {
        return null;
    }

    public Book createOrUpdateBookFallback(Book book) {
        return null;
    }

    public Book deleteBookByIdFallback(String id) {
        return null;
    }
}
