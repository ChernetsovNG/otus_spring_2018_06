package ru.nchernetsov.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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

    public void saveAllData() {
        authorRepository.saveAll(testData.getAuthorsMap().values());
        bookRepository.saveAll(testData.getBooksMap().values());
        commentRepository.saveAll(testData.getCommentsMap().values());
        genreRepository.saveAll(testData.getGenresMap().values());
    }

    public void clearAllData() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        commentRepository.deleteAll();
        genreRepository.deleteAll();
    }

}
