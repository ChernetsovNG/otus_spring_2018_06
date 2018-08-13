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
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.getBookTitles;
import static ru.nchernetsov.Utils.getGenreNames;

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("test")
public class GenreRepositoryTest extends MongoDBTest {

    @Autowired
    private GenreRepository genreRepository;

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
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(5);

        assertThat(getGenreNames(genres)).containsExactlyInAnyOrder(
                "Фантастика", "Приключения", "Хоррор", "Проза", "Программирование");
    }

    @Test
    public void insertTest() {
        genreRepository.save(new Genre("Естественные науки"));

        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(6);

        assertThat(getGenreNames(genres)).containsExactlyInAnyOrder(
                "Фантастика", "Приключения", "Хоррор", "Проза", "Программирование", "Естественные науки");
    }

    @Test
    public void getByName() {
        Optional<Genre> genreOptional = genreRepository.findByName("Проза");

        assertThat(genreOptional).isPresent();

        Genre genre = genreOptional.get();

        assertThat(genre.getName()).isEqualTo("Проза");

        List<Book> books = genre.getBookIds().stream()
                .map(bookId -> bookRepository.findById(bookId))
                .map(Optional::get)
                .collect(Collectors.toList());

        assertThat(books).hasSize(3);
        assertThat(getBookTitles(books)).containsExactlyInAnyOrder(
                "Белый клык", "Война и мир", "Праздник, который всегда с тобой");
    }

    @Test
    public void removeByNameTest() {
        genreRepository.deleteByName("Хоррор");

        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(4);

        assertThat(getGenreNames(genres)).containsExactlyInAnyOrder(
                "Фантастика", "Приключения", "Проза", "Программирование");
    }

}
