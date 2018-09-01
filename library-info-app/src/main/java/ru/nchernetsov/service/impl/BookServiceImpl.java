package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> findOne(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Book> createOrUpdateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book deleteBookById(String id) {
        Mono<Book> bookMono = bookRepository.findById(id);
        bookMono.subscribe(book -> bookRepository.deleteById(book.getId()));
        return bookMono.block();
    }

}
