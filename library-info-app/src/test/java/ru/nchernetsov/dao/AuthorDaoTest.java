package ru.nchernetsov.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Author;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    public void getByIdTest() {
        assertThat(authorDao.getById(11).getName()).isEqualTo("Джек Лондон");
    }

    @Test
    public void getAllTest() {
        List<Author> authors = authorDao.getAll();

        assertThat(authors).hasSize(6);
        List<String> authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertThat(authorNames).containsExactlyInAnyOrder(
            "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Лев Толстой", "Брюс Эккель", "Эрнест Хеммингуэй");
    }

    @Test
    public void addAuthorTest() {
        authorDao.addAuthor(new Author(100, "Ник Перумов"));

        List<Author> authors = authorDao.getAll();
        assertThat(authors).hasSize(7);
        List<String> authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertThat(authorNames).containsExactlyInAnyOrder(
            "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Лев Толстой", "Брюс Эккель", "Эрнест Хеммингуэй", "Ник Перумов");
    }

    @Test
    public void deleteAuthorTest() {
        authorDao.deleteAuthor(new Author(13, "Лев Толстой"));

        List<Author> authors = authorDao.getAll();
        assertThat(authors).hasSize(5);
        List<String> authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertThat(authorNames).containsExactlyInAnyOrder(
            "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Брюс Эккель", "Эрнест Хеммингуэй");
    }

}
