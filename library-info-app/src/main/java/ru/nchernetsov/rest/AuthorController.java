package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/authors/edit")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        List<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        author.setBookIds(authorBooks);

        Author createdAuthor = authorService.createOrUpdateAuthor(author);

        return ResponseEntity.ok(createdAuthor);
    }

    @DeleteMapping(value = "/authors/{authorId}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable(name = "authorId") String authorId) {
        Author author = authorService.deleteAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

}
