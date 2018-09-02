package ru.nchernetsov.rest;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.repository.AuthorRepository;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.repository.GenreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GenreRepository genreRepository;

    @After
    public void clearAllData() {
        authorRepository.deleteAll().then().block();
        bookRepository.deleteAll().then().block();
        commentRepository.deleteAll().then().block();
        genreRepository.deleteAll().then().block();
    }

    @Test
    public void getAuthorsTest() {
        webTestClient.get().uri("/authors")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Author.class);
    }

    @Test
    public void createAuthorTest() {
        Author newAuthor = new Author("Nikita Chernetsov");

        webTestClient.post().uri("/authors")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(newAuthor), Author.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.name").isEqualTo("Nikita Chernetsov");
    }

    @Test
    public void updateAuthorTest() {
        Author author = authorRepository.save(new Author("Test Author")).block();

        Author newAuthor = new Author("Test Author NEW");

        webTestClient.put()
            .uri("/authors")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(newAuthor), Author.class)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.name").isEqualTo("Test Author NEW");
    }

    @Test
    public void deleteAuthorTest() {

    }
}