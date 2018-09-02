package ru.nchernetsov.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    public Flux<Book> booksList() {
        return bookService.findAll();
    }

    @PostMapping(value = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> createBook(@RequestBody Book book) {
        return createOrUpdateBook(book);
    }

    @PutMapping(value = "/books")
    public Mono<ResponseEntity<Book>> updateBook(@RequestBody Book book) {
        return createOrUpdateBook(book)
            .map(updatedBook -> new ResponseEntity<>(updatedBook, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/books/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable(name = "id") String id) {
        return bookService.deleteBookById(id)
            .then(Mono.just(new ResponseEntity<>(HttpStatus.OK)));
    }

    private Mono<Book> createOrUpdateBook(@RequestBody Book book) {
        // Если у книги изменились авторы, то изменяем книги у авторов
        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            author.addBook(book);
        }
        authorService.createOrUpdateAuthorList(authors).subscribe();

        // Если у книги изменились жанры, то изменяем книги у жанров
        List<Genre> genres = book.getGenres();
        for (Genre genre : genres) {
            genre.addBook(book);
        }
        genreService.createOrUpdateGenreList(genres).subscribe();

        return bookService.createOrUpdateBook(book);
    }

}
