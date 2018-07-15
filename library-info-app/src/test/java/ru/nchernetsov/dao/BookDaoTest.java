package ru.nchernetsov.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class BookDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private GenreDao genreDao;

    @Test
    public void getByIdTest() {
        assertThat(bookDao.getById(14).getTitle()).isEqualTo("Философия Java");
    }

    @Test
    public void getAllTest() {
        List<Book> books = bookDao.getAll();

        assertThat(books).hasSize(6);
        List<String> bookTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertThat(bookTitles).containsExactlyInAnyOrder(
            "Бойня номер пять, или Крестовый поход детей", "Белый клык", "Оно", "Война и мир", "Философия Java",
            "Праздник, который всегда с тобой");
    }

    @Test
    public void addBookTest() {
        Book newBook = new Book(100, "Путь Ruby");
        newBook.addGenre(new Genre(14, "Программирование"));

        bookDao.addBook(newBook);

        List<Book> books = bookDao.getAll();

        assertThat(books).hasSize(7);
        List<String> bookTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertThat(bookTitles).containsExactlyInAnyOrder(
            "Бойня номер пять, или Крестовый поход детей", "Белый клык", "Оно", "Война и мир", "Философия Java",
            "Праздник, который всегда с тобой", "Путь Ruby");
    }

    @Test
    public void deleteBookTest() {
        bookDao.deleteBook(new Book(10, "Бойня номер пять, или Крестовый поход детей"));

        List<Book> books = bookDao.getAll();

        assertThat(books).hasSize(5);
        List<String> bookTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertThat(bookTitles).containsExactlyInAnyOrder(
            "Белый клык", "Оно", "Война и мир", "Философия Java", "Праздник, который всегда с тобой");
    }

    @Test
    public void getBooksByAuthorTest() {
        List<Book> booksByAuthor = bookDao.getBooksByAuthor(new Author(10, "Курт Воннегут"));

        assertThat(booksByAuthor).hasSize(1);
        assertThat(booksByAuthor.get(0).getTitle()).isEqualTo("Бойня номер пять, или Крестовый поход детей");
    }

    @Test
    public void getBooksByGenreTest() {
        List<Book> booksByGenre = bookDao.getBooksByGenre(new Genre(13, "Проза"));

        assertThat(booksByGenre).hasSize(3);
        List<String> bookTitles = booksByGenre.stream().map(Book::getTitle).collect(Collectors.toList());
        assertThat(bookTitles).containsExactlyInAnyOrder(
            "Белый клык", "Война и мир", "Праздник, который всегда с тобой");
    }

    @Test
    public void addAuthorToBookTest() {
        Author newAuthor = new Author(100, "Плагиатор");

        authorDao.addAuthor(newAuthor);
        bookDao.addAuthorToBook(new Book(14, "Философия Java"), newAuthor);

        Book book = bookDao.getById(14);
        List<Author> bookAuthors = book.getAuthors();
        List<String> authorNames = bookAuthors.stream().map(Author::getName).collect(Collectors.toList());

        assertThat(authorNames).hasSize(2);
        assertThat(authorNames).containsExactlyInAnyOrder("Брюс Эккель", "Плагиатор");
    }

    @Test
    public void addGenreToBookTest() {
        Genre newGenre = new Genre(100, "Антивоенная литература");

        genreDao.addGenre(newGenre);
        bookDao.addGenreToBook(new Book(10, "Бойня номер пять, или Крестовый поход детей"), newGenre);

        Book book = bookDao.getById(10);
        List<Genre> bookGenres = book.getGenres();
        List<String> genreNames = bookGenres.stream().map(Genre::getName).collect(Collectors.toList());

        assertThat(genreNames).hasSize(2);
        assertThat(genreNames).containsExactlyInAnyOrder("Фантастика", "Антивоенная литература");
    }
}
