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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Author> createAuthor(@RequestBody Author author) {
        author.setId(UUID.randomUUID().toString());

        Flux<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        authorBooks.collectList().subscribe(author::setBookIds);

        return authorService.createOrUpdateAuthor(author);
    }

    @PutMapping(value = "/authors")
    public Mono<ResponseEntity<Author>> updateAuthor(@RequestBody Author author) {
        Flux<Book> authorBooks = authorService.getAuthorBooks(author.getId());
        authorBooks.collectList().subscribe(author::setBookIds);

        return authorService.createOrUpdateAuthor(author)
            .map(updatedAuthor -> new ResponseEntity<>(updatedAuthor, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/authors/{authorId}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable(name = "authorId") String authorId) {
        return authorService.deleteAuthorById(authorId)
            .then(Mono.just(new ResponseEntity<>(HttpStatus.OK)));
    }

}
