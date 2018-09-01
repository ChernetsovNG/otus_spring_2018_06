package ru.nchernetsov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.repository.GenreRepository;

@DataMongoTest
@ActiveProfiles("test")
public class MongoDBTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GenreRepository genreRepository;

    private TestData testData = new TestData();

    protected void saveAllData() {
        authorRepository.saveAll(testData.getAuthorsMap().values()).then().block();
        bookRepository.saveAll(testData.getBooksMap().values()).then().block();
        commentRepository.saveAll(testData.getCommentsMap().values()).then().block();
        genreRepository.saveAll(testData.getGenresMap().values()).then().block();
    }

    protected void clearAllData() {
        authorRepository.deleteAll().then().block();
        bookRepository.deleteAll().then().block();
        commentRepository.deleteAll().then().block();
        genreRepository.deleteAll().then().block();
    }

}
