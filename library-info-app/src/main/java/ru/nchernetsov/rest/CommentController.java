package ru.nchernetsov.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.service.BookService;
import ru.nchernetsov.service.CommentService;

import java.util.UUID;

@RestController
@CrossOrigin
public class CommentController {

    private final BookService bookService;

    private final CommentService commentService;

    public CommentController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/comments")
    public Flux<Comment> getAllComments() {
        return commentService.getAll();
    }

    @GetMapping(value = "/comments/books/{bookId}")
    public Flux<Comment> bookCommentsList(@PathVariable(value = "bookId") String bookId) {
        Mono<Book> bookMono = bookService.findOne(bookId);
        return bookMono.map(Book::getComments).flatMapMany(Flux::fromIterable);
    }

    @PostMapping(value = "/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Comment> createComment(@RequestBody Comment comment) {
        comment.setId(UUID.randomUUID().toString());
        return commentService.addCommentToBookById(comment.getBookId(), comment);
    }

    @PutMapping(value = "/comments")
    public Mono<ResponseEntity<Comment>> updateComment(@RequestBody Comment comment) {
        return commentService.addCommentToBookById(comment.getBookId(), comment)
            .map(updatedComment -> new ResponseEntity<>(updatedComment, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable(name = "commentId") String commentId) {
        return commentService.deleteCommentById(commentId)
            .then(Mono.just(new ResponseEntity<>(HttpStatus.OK)));
    }
}
