package ru.nchernetsov.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    public void getByIdTest() {
        assertThat(genreDao.getById(14).getName()).isEqualTo("Программирование");
    }

    @Test
    public void getAllTest() {
        List<Genre> genres = genreDao.getAll();
        assertThat(genres).hasSize(5);

        List<String> genreNames = genres.stream().map(Genre::getName).collect(Collectors.toList());
        assertThat(genreNames).containsExactlyInAnyOrder(
            "Фантастика", "Приключения", "Хоррор", "Проза", "Программирование");
    }

    @Test
    public void addGenreTest() {
        genreDao.addGenre(new Genre(100, "Естественные науки"));

        List<Genre> genres = genreDao.getAll();
        assertThat(genres).hasSize(6);

        List<String> genreNames = genres.stream().map(Genre::getName).collect(Collectors.toList());
        assertThat(genreNames).containsExactlyInAnyOrder(
            "Фантастика", "Приключения", "Хоррор", "Проза", "Программирование", "Естественные науки");
    }

    @Test
    public void deleteGenreTest() {
        genreDao.deleteGenre(new Genre(12, "Хоррор"));

        List<Genre> genres = genreDao.getAll();
        assertThat(genres).hasSize(4);

        List<String> genreNames = genres.stream().map(Genre::getName).collect(Collectors.toList());
        assertThat(genreNames).containsExactlyInAnyOrder(
            "Фантастика", "Приключения", "Проза", "Программирование");
    }
}
