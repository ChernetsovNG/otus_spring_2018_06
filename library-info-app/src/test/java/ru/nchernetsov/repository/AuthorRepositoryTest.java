package ru.nchernetsov.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.getAuthorNames;

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("test")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Before
    public void beforeEachTest() {
        authorRepository.save(new Author("Курт Воннегут"));
        authorRepository.save(new Author("Джек Лондон"));

        Author stevenKing = new Author("Стивен Кинг");
        stevenKing.setBooks(Collections.singletonList(new Book("Оно")));
        authorRepository.save(stevenKing);

        authorRepository.save(new Author("Лев Толстой"));
        authorRepository.save(new Author("Брюс Эккель"));
        authorRepository.save(new Author("Эрнест Хеммингуэй"));
    }

    @After
    public void afterEachTest() {
        authorRepository.deleteAll();
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
        assertThat(readAuthor.getBooks()).hasSize(1);
        assertThat(readAuthor.getBooks().get(0).getTitle()).isEqualTo("Оно");
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
