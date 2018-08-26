package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.service.AuthorService;

import java.util.List;

@RestController
@CrossOrigin
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return authorService.findAll();
    }

    @PostMapping(value = "/authors")
    public ResponseEntity<?> editAuthor(@RequestBody Author author) {
        List<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        author.setBookIds(authorBooks);

        authorService.createOrUpdateAuthor(author);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/authors/{authorId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable(name = "authorId") String authorId) {
        authorService.deleteAuthorById(authorId);
        return ResponseEntity.ok().build();
    }

}
