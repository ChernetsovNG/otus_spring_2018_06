package ru.nchernetsov.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.service.AuthorService;
import ru.nchernetsov.service.BookService;
import ru.nchernetsov.service.CommentService;
import ru.nchernetsov.service.GenreService;

import java.util.List;

@RestController
@CrossOrigin
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    public BookController(BookService bookService, AuthorService authorService,
                          GenreService genreService, CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/books")
    public List<Book> booksList() {
        return bookService.findAll();
    }

    @PostMapping(value = "/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return createOrUpdateBook(book);
    }

    @PutMapping(value = "/books")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return createOrUpdateBook(book);
    }

    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable(name = "id") String id) {
        Book book = bookService.deleteBookById(id);
        return ResponseEntity.ok(book);
    }

    private ResponseEntity<Book> createOrUpdateBook(@RequestBody Book book) {
        // Если у книги изменились авторы, то изменяем книги у авторов
        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            author.addBook(book);
        }
        authorService.createOrUpdateAuthorList(authors);

        // Если у книги изменились жанры, то изменяем книги у жанров
        List<Genre> genres = book.getGenres();
        for (Genre genre : genres) {
            genre.addBook(book);
        }
        genreService.createOrUpdateGenreList(genres);

        Book savedBook = bookService.createOrUpdateBook(book);

        return ResponseEntity.ok(savedBook);
    }

}
