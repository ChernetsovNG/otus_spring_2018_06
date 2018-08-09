package ru.nchernetsov.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("test")
public class CommentRepositoryTest extends MongoDBTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void beforeEachTest() {
        saveAllData();
    }

    @After
    public void afterEachTest() {
        clearAllData();
    }


    @Test
    public void getAllTest() {
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(4);

        Set<String> commentBooks = comments.stream()
                .map(Comment::getBook)
                .filter(Objects::nonNull)
                .map(bookId -> bookRepository.findById(bookId))
                .map(Optional::get)
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        assertThat(commentBooks).containsExactlyInAnyOrder(
                "Бойня номер пять, или Крестовый поход детей", "Оно");
    }

    @Test
    public void insertTest() {
        commentRepository.save(new Comment("Test comment"));

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(5);
    }

}
