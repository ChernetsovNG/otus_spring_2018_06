package ru.nchernetsov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.nchernetsov.TestData;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.repository.GenreRepository;

@Service
public class InitApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitApplicationService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public InitApplicationService(BookRepository bookRepository, AuthorRepository authorRepository,
                                  GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeTestData() {
        LOGGER.info("Initialize test data");

        authorRepository.deleteAll().then().block();
        bookRepository.deleteAll().then().block();
        commentRepository.deleteAll().then().block();
        genreRepository.deleteAll().then().block();

        TestData testData = new TestData();

        authorRepository.saveAll(testData.getAuthorsMap().values()).then().block();
        genreRepository.saveAll(testData.getGenresMap().values()).then().block();
        bookRepository.saveAll(testData.getBooksMap().values()).then().block();
        commentRepository.saveAll(testData.getCommentsMap().values()).then().block();

        LOGGER.info("Initialization completed");
    }

}
