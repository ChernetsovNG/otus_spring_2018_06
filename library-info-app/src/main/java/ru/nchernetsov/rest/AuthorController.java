package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.service.AuthorService;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        author.setId(UUID.randomUUID().toString());

        List<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        author.setBookIds(authorBooks);

        Author createdAuthor = authorService.createOrUpdateAuthor(author);

        return ResponseEntity.ok(createdAuthor);
    }

    @PutMapping(value = "/authors")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        List<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        author.setBookIds(authorBooks);

        Author updatedAuthor = authorService.createOrUpdateAuthor(author);

        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping(value = "/authors/{authorId}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable(name = "authorId") String authorId) {
        Author author = authorService.deleteAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

}
