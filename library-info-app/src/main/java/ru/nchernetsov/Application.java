package ru.nchernetsov;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.repository.GenreRepository;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        BookRepository bookRepository = context.getBean(BookRepository.class);
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        CommentRepository commentRepository = context.getBean(CommentRepository.class);
        GenreRepository genreRepository = context.getBean(GenreRepository.class);

        List<Book> books = bookRepository.findAll();
        List<Author> authors = authorRepository.findAll();
        List<Comment> comments = commentRepository.findAll();
        List<Genre> genres = genreRepository.findAll();

        Console.main(args);
    }
}
