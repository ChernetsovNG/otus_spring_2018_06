package ru.nchernetsov.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.TestData;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.getGenreNames;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ShellCommandsTest {

    @Autowired
    private Shell shell;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GenreRepository genreRepository;

    private TestData testData = new TestData();

    @Before
    public void beforeEachTest() {
        authorRepository.saveAll(testData.getAuthorsMap().values());
        bookRepository.saveAll(testData.getBooksMap().values());
        commentRepository.saveAll(testData.getCommentsMap().values());
        genreRepository.saveAll(testData.getGenresMap().values());
    }

    @After
    public void afterEachTest() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        commentRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    public void findBookByTitleTest() {
        Book book = (Book) shell.evaluate(() ->
                "find-book-by-title --title Оно");

        assertThat(book.getTitle()).isEqualTo("Оно");

        assertThat(book.getAuthors()).hasSize(1);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Стивен Кинг");

        assertThat(book.getGenres()).hasSize(2);
        assertThat(getGenreNames(book.getGenres())).containsExactlyInAnyOrder("Фантастика", "Хоррор");

        assertThat(book.getComments()).hasSize(2);
    }

}
