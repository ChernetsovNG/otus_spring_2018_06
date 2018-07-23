package ru.nchernetsov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.impl.CommentRepositoryJpaImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = CommentRepositoryJpaImpl.class)
@ActiveProfiles("test")
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void getByIdTest() {
        Comment comment = commentRepository.getById(11);

        assertThat(comment.getComment()).contains("одно из лучших произведений");
        assertThat(comment.getBook().getTitle()).isEqualTo("Бойня номер пять, или Крестовый поход детей");
    }

    @Test
    public void getAllTest() {
        List<Comment> comments = commentRepository.getAll();
        assertThat(comments).hasSize(4);

        Set<String> commentBooks = comments.stream().map(Comment::getBook).map(Book::getTitle).collect(Collectors.toSet());
        assertThat(commentBooks).containsExactlyInAnyOrder(
            "Бойня номер пять, или Крестовый поход детей", "Оно");
    }

    @Test
    public void insertTest() {
        commentRepository.insert(new Comment("Test comment"));

        List<Comment> comments = commentRepository.getAll();
        assertThat(comments).hasSize(5);
    }
}
