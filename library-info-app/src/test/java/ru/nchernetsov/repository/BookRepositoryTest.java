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
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.Utils.getBookTitles;
import static ru.nchernetsov.Utils.getGenreNames;

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("test")
public class BookRepositoryTest extends MongoDBTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

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
        List<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(6);
        assertThat(getBookTitles(books)).containsExactlyInAnyOrder(
                "Бойня номер пять, или Крестовый поход детей", "Белый клык", "Оно", "Война и мир", "Философия Java",
                "Праздник, который всегда с тобой");
    }

    @Test
    public void insertTest() {
        Optional<Genre> genreOptional = genreRepository.findByName("Программирование");

        assertThat(genreOptional).isPresent();

        Book newBook = new Book("Путь Ruby",
                Collections.singletonList(new Author("Хэл Фултон")),
                Collections.singletonList(genreOptional.get()));

        bookRepository.save(newBook);

        List<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(7);
        assertThat(getBookTitles(books)).containsExactlyInAnyOrder(
                "Бойня номер пять, или Крестовый поход детей", "Белый клык", "Оно", "Война и мир", "Философия Java",
                "Праздник, который всегда с тобой", "Путь Ruby");
    }

    @Test
    public void updateTest() {
        Optional<Book> itBookOptional = bookRepository.findByTitle("Оно");

        assertThat(itBookOptional).isPresent();

        Book itBook = itBookOptional.get();

        Optional<Genre> genreOptional = genreRepository.findByName("Проза");

        assertThat(genreOptional).isPresent();

        Genre genre = genreOptional.get();

        itBook.addGenre(genre);

        bookRepository.save(itBook);

        Optional<Book> itBookUpdatedOptional = bookRepository.findByTitle("Оно");

        assertThat(itBookUpdatedOptional).isPresent();

        Book itBookUpdated = itBookUpdatedOptional.get();

        List<Genre> genres = itBookUpdated.getGenres();

        assertThat(genres).hasSize(3);
        assertThat(getGenreNames(genres)).containsExactlyInAnyOrder(
                "Фантастика", "Хоррор", "Проза");
    }

    @Test
    public void getByTitleTest() {
        Optional<Book> bookOptional = bookRepository.findByTitle("Праздник, который всегда с тобой");

        assertThat(bookOptional).isPresent();

        Book book = bookOptional.get();

        assertThat(book.getTitle()).isEqualTo("Праздник, который всегда с тобой");
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Эрнест Хеммингуэй");
    }

    @Test
    public void removeByTitleTest() {
        bookRepository.deleteByTitle("Бойня номер пять, или Крестовый поход детей");

        List<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(5);
        assertThat(getBookTitles(books)).containsExactlyInAnyOrder(
                "Белый клык", "Оно", "Война и мир", "Философия Java", "Праздник, который всегда с тобой");
    }

}
