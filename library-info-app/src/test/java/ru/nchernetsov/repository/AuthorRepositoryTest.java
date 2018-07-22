package ru.nchernetsov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.repository.impl.AuthorRepositoryJpaImpl;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.getAuthorNames;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = {AuthorRepositoryJpaImpl.class})
@ActiveProfiles("test")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void getByIdTest() {
        assertThat(authorRepository.getById(11).getName()).isEqualTo("Джек Лондон");
    }

    @Test
    public void getAllTest() {
        List<Author> authors = authorRepository.getAll();

        assertThat(authors).hasSize(6);
        List<String> authorNames = authors.stream().map(Author::getName).collect(Collectors.toList());
        assertThat(authorNames).containsExactlyInAnyOrder(
            "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Лев Толстой", "Брюс Эккель", "Эрнест Хеммингуэй");
    }

    @Test
    public void insertTest() {
        authorRepository.insert(new Author("Ник Перумов"));

        List<Author> authors = authorRepository.getAll();
        assertThat(authors).hasSize(7);
        assertThat(getAuthorNames(authors)).containsExactlyInAnyOrder(
            "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Лев Толстой", "Брюс Эккель", "Эрнест Хеммингуэй", "Ник Перумов");
    }

    @Test
    public void getByName() {
        Author author = authorRepository.getByName("Стивен Кинг");

        assertThat(author.getName()).isEqualTo("Стивен Кинг");
        assertThat(author.getBooks()).hasSize(1);
        assertThat(author.getBooks().get(0).getTitle()).isEqualTo("Оно");
    }

    @Test
    public void removeByNameTest() {
        authorRepository.removeByName("Лев Толстой");

        List<Author> authors = authorRepository.getAll();
        assertThat(authors).hasSize(5);
        assertThat(getAuthorNames(authors)).containsExactlyInAnyOrder(
            "Курт Воннегут", "Джек Лондон", "Стивен Кинг", "Брюс Эккель", "Эрнест Хеммингуэй");
    }
}
