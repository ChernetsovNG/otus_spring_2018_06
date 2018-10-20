package ru.nchernetsov.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.MongoDBTest;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.service.impl.CommentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
@Import(value = {CommentServiceImpl.class})
@ActiveProfiles("test")
public class CommentServiceTest extends MongoDBTest {

    @Autowired
    private CommentService commentService;

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
    public void addCommentToBookTest1() {
        commentService.addCommentToBookByTitle("Праздник, который всегда с тобой",
            new Comment("Я не особо люблю ни биографии, ни автобиографии писателей. Мне больше по душе погружаться в их придуманный мир. Но после прочтения \"Праздника...\" возникло неудержимое " +
                "желание познакомиться с Хемингуэем поближе. Хотя я знакома с его произведениями, но почему-то именно эта книга дала толчок на более глубокое знакомство с писателем. Не буду " +
                "рассказывать, что я о нем узнала, отзыв не об этом. Не знаю, что меня больше пленило в \"Празднике...\". Великолепнейшее описание Парижа, сам автор, его жизнь или люди, с " +
                "которыми Эрнест дружил и общался, а может его неповторимый стиль и слог. Не знаю. Наверное все. Читала не отрываясь. Кстати, именно после этой книги также захотелось поближе" +
                " познакомиться со Скоттом Фицджеральдом."));

        Optional<Book> bookOptional = bookRepository.findByTitle("Праздник, который всегда с тобой");

        assertThat(bookOptional).isPresent();

        Book book = bookOptional.get();

        List<Comment> comments = book.getComments();

        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getComment()).contains("неудержимое желание познакомиться с Хемингуэем поближе");
    }
}
