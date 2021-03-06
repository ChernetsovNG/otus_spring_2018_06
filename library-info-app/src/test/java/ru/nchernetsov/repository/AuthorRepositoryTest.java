package ru.nchernetsov.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.MongoDBTest;
import ru.nchernetsov.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.getAuthorNames;

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("test")
public class AuthorRepositoryTest extends MongoDBTest {

    @Autowired
    private AuthorRepository authorRepository;

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
        List<Author> authors = authorRepository.findAll();

        assertThat(authors).hasSize(6);
        List<String> authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertThat(authorNames).containsExactlyInAnyOrder(
                "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Лев Толстой", "Брюс Эккель", "Эрнест Хеммингуэй");
    }

    @Test
    public void insertTest() {
        authorRepository.save(new Author("Ник Перумов"));

        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(7);
        assertThat(getAuthorNames(authors)).containsExactlyInAnyOrder(
                "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Лев Толстой", "Брюс Эккель", "Эрнест Хеммингуэй", "Ник Перумов");
    }

    @Test
    public void findByNameTest() {
        Optional<Author> authorOptional = authorRepository.findByName("Стивен Кинг");

        assertThat(authorOptional).isPresent();

        Author readAuthor = authorOptional.get();

        assertThat(readAuthor.getName()).isEqualTo("Стивен Кинг");
        assertThat(readAuthor.getBookIds()).hasSize(1);
        //assertThat(readAuthor.getBooks().get(0).getTitle()).isEqualTo("Оно");
    }

    @Test
    public void removeByNameTest() {
        authorRepository.deleteByName("Лев Толстой");

        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(5);
        assertThat(getAuthorNames(authors)).containsExactlyInAnyOrder(
                "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Брюс Эккель", "Эрнест Хеммингуэй");
    }
}
