package ru.nchernetsov.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.BookRepository;

import java.util.Optional;

@ShellComponent
public class ShellCommands {

    private final BookRepository bookRepository;

    public ShellCommands(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ShellMethod("Find book by title")
    @Transactional(readOnly = true)
    public Book findBookByTitle(String title) {
        Optional<Book> bookOptional = bookRepository.findByTitle(title);
        return bookOptional.orElse(null);
    }
}