package ru.nchernetsov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    public Flux<Author> getAuthors() {
        return authorService.findAll();
    }

    @PostMapping(value = "/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        author.setId(UUID.randomUUID().toString());

        List<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        author.setBookIds(authorBooks);

        Mono<Author> createdAuthor = authorService.createOrUpdateAuthor(author);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor.block());
    }

    @PutMapping(value = "/authors")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        List<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        author.setBookIds(authorBooks);

        Mono<Author> updatedAuthor = authorService.createOrUpdateAuthor(author);

        return ResponseEntity.ok(updatedAuthor.block());
    }

    @DeleteMapping(value = "/authors/{authorId}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable(name = "authorId") String authorId) {
        Mono<Author> author = authorService.deleteAuthorById(authorId);
        return ResponseEntity.ok(author.block());
    }

}
