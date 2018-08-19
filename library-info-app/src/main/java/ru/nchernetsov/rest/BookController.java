package ru.nchernetsov.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.service.AuthorService;
import ru.nchernetsov.service.BookService;
import ru.nchernetsov.service.CommentService;
import ru.nchernetsov.service.GenreService;

import java.util.List;

@Controller
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

    @GetMapping(value = {"/", "/books"})
    public String booksList(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping(value = {"/books/edit", "/books/edit/{id}"})
    public String createOrEditBookForm(Model model, @PathVariable(required = false, name = "id") String id) {
        if (id != null) {
            model.addAttribute("book", bookService.findOne(id));
        } else {
            model.addAttribute("book", new Book());
        }

        model.addAttribute("allAuthors", authorService.findAll());
        model.addAttribute("allGenres", genreService.findAll());

        return "editBook";
    }

    @PostMapping(value = "/books/edit")
    public String editBook(Model model, Book book) {

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

        // Восстанавливаем комментарии (которые не поулчилось передать в POST-запросе с формы)
        List<Comment> comments = commentService.getBookComments(book.getId());
        book.setComments(comments);

        bookService.createOrUpdateBook(book);
        model.addAttribute("books", bookService.findAll());

        return "redirect:/books";
    }

    @GetMapping(value = "/books/delete/{id}")
    public String deleteBook(Model model, @PathVariable(name = "id") String id) {
        bookService.deleteBookById(id);
        model.addAttribute("books", bookService.findAll());

        return "redirect:/books";
    }

}