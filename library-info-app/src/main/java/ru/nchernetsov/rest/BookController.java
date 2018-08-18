package ru.nchernetsov.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.service.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/")
    public String rootBooksList(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping(value = "/books")
    public String booksList(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping(value = {"/books/edit", "/books/edit/{id}"})
    public String bookEditForm(Model model, @PathVariable(required = false, name = "id") String id) {
        if (id != null) {
            model.addAttribute("book", bookService.findOne(id));
        } else {
            model.addAttribute("book", new Book());
        }
        return "editBook";
    }

    @PostMapping(value = "/books/edit")
    public String editBook(Model model, Book book) {
        bookService.createOrUpdateBook(book);
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping(value = "/books/delete/{id}")
    public String deleteBook(Model model, @PathVariable(name = "id") String id) {
        bookService.deleteBookById(id);
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

}
