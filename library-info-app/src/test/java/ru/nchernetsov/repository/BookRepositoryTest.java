package ru.nchernetsov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.repository.impl.BookRepositoryJpaImpl;
import ru.nchernetsov.repository.impl.GenreRepositoryJpaImpl;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.getBookTitles;
import static ru.nchernetsov.Utils.getGenreNames;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = {BookRepositoryJpaImpl.class, GenreRepositoryJpaImpl.class})
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void getByIdTest() {
        assertThat(bookRepository.getById(13).getTitle()).isEqualTo("Война и мир");
    }

    @Test
    public void getAllTest() {
        List<Book> books = bookRepository.getAll();

        assertThat(books).hasSize(6);
        assertThat(getBookTitles(books)).containsExactlyInAnyOrder(
            "Бойня номер пять, или Крестовый поход детей", "Белый клык", "Оно", "Война и мир", "Философия Java",
            "Праздник, который всегда с тобой");
    }

    @Test
    public void insertTest() {
        Genre genre = genreRepository.getByName("Программирование");

        Book newBook = new Book("Путь Ruby",
            Collections.singletonList(new Author("Хэл Фултон")),
            Collections.singletonList(genre));

        bookRepository.insert(newBook);

        List<Book> books = bookRepository.getAll();

        assertThat(books).hasSize(7);
        assertThat(getBookTitles(books)).containsExactlyInAnyOrder(
            "Бойня номер пять, или Крестовый поход детей", "Белый клык", "Оно", "Война и мир", "Философия Java",
            "Праздник, который всегда с тобой", "Путь Ruby");
    }

    @Test
    public void updateTest() {
        Book itBook = bookRepository.getById(12);

        Genre genre = genreRepository.getByName("Проза");

        itBook.addGenre(genre);

        bookRepository.update(itBook);
        em.flush();

        Book itBookUpdated = bookRepository.getById(12);

        List<Genre> genres = itBookUpdated.getGenres();

        assertThat(genres).hasSize(3);
        assertThat(getGenreNames(genres)).containsExactlyInAnyOrder(
            "Фантастика", "Хоррор", "Проза");
    }

    @Test
    public void getByTitleTest() {
        Book book = bookRepository.getByTitle("Праздник, который всегда с тобой");

        assertThat(book.getTitle()).isEqualTo("Праздник, который всегда с тобой");
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Эрнест Хеммингуэй");
    }

    @Test
    public void removeByTitleTest() {
        bookRepository.removeByTitle("Бойня номер пять, или Крестовый поход детей");

        List<Book> books = bookRepository.getAll();

        assertThat(books).hasSize(5);
        assertThat(getBookTitles(books)).containsExactlyInAnyOrder(
            "Белый клык", "Оно", "Война и мир", "Философия Java", "Праздник, который всегда с тобой");
    }
}
