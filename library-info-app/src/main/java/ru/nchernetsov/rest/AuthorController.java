package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.AuthorWithBooks;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String getAuthors(Model model) {
        List<Author> authors = authorService.findAll();

        List<AuthorWithBooks> authorsWithBooks = authors.stream()
            .map(author -> new AuthorWithBooks(author, authorService.getAuthorBooks(author.getId())))
            .collect(Collectors.toList());

        model.addAttribute("authors", authors);

        return "authors";
    }

    @GetMapping(value = {"/authors/edit", "/authors/edit/{authorId}"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createOrEditAuthorForm(Model model, @PathVariable(required = false, name = "authorId") String authorId) {
        if (authorId != null) {
            model.addAttribute("author", authorService.findOne(authorId));
        } else {
            model.addAttribute("author", new Author());
        }
        return "editAuthor";
    }

    @PostMapping(value = "/authors/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editAuthor(Model model, Author author) {
        List<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        author.setBookIds(authorBooks);

        authorService.createOrUpdateAuthor(author);
        model.addAttribute("authors", authorService.findAll());

        return "redirect:/authors";
    }

    @GetMapping(value = "/authors/delete/{authorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAuthor(Model model, @PathVariable(name = "authorId") String authorId) {
        authorService.deleteAuthorById(authorId);
        model.addAttribute("authors", authorService.findAll());
        return "redirect:/authors";
    }

}
