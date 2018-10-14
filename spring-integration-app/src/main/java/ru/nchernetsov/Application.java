package ru.nchernetsov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.config.EnableIntegration;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.config.InfrastructureConfiguration;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.repository.GenreRepository;

import java.util.List;

@SpringBootApplication
@EnableIntegration
// @ImportResource("integration-context.xml")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        new Application().start(context);
    }

    public void start(ConfigurableApplicationContext context) {
        resetDatabase(context);
        initDatabase(context);

        InfrastructureConfiguration.BookService bookService = context.getBean(InfrastructureConfiguration.BookService.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);

        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            bookService.book(book);
        }

    }

    private void resetDatabase(ConfigurableApplicationContext context) {
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);
        CommentRepository commentRepository = context.getBean(CommentRepository.class);

        authorRepository.deleteAll();
        genreRepository.deleteAll();
        bookRepository.deleteAll();
        commentRepository.deleteAll();
    }

    private void initDatabase(ConfigurableApplicationContext context) {
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);
        CommentRepository commentRepository = context.getBean(CommentRepository.class);

        TestData testData = new TestData();

        authorRepository.saveAll(testData.getAuthorsMap().values());
        genreRepository.saveAll(testData.getGenresMap().values());
        bookRepository.saveAll(testData.getBooksMap().values());
        commentRepository.saveAll(testData.getCommentsMap().values());
    }

}
