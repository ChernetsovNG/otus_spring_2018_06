package ru.nchernetsov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.impl.BookRepositoryJpaImpl;
import ru.nchernetsov.repository.impl.CommentRepositoryJpaImpl;
import ru.nchernetsov.service.impl.CommentServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = {BookRepositoryJpaImpl.class, CommentRepositoryJpaImpl.class, CommentServiceImpl.class})
@ActiveProfiles("test")
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void addCommentToBookTest1() {
        commentService.addCommentToBook("Праздник, который всегда с тобой",
            "Я не особо люблю ни биографии, ни автобиографии писателей. Мне больше по душе погружаться в их придуманный мир. Но после прочтения \"Праздника...\" возникло неудержимое " +
                "желание познакомиться с Хемингуэем поближе. Хотя я знакома с его произведениями, но почему-то именно эта книга дала толчок на более глубокое знакомство с писателем. Не буду " +
                "рассказывать, что я о нем узнала, отзыв не об этом. Не знаю, что меня больше пленило в \"Празднике...\". Великолепнейшее описание Парижа, сам автор, его жизнь или люди, с " +
                "которыми Эрнест дружил и общался, а может его неповторимый стиль и слог. Не знаю. Наверное все. Читала не отрываясь. Кстати, именно после этой книги также захотелось поближе" +
                " познакомиться со Скоттом Фицджеральдом.");

        Book book = bookRepository.getByTitle("Праздник, который всегда с тобой");

        List<Comment> comments = book.getComments();

        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getComment()).contains("неудержимое желание познакомиться с Хемингуэем поближе");
    }

}
