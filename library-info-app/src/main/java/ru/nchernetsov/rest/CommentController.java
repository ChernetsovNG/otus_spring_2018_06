package ru.nchernetsov.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.service.BookService;
import ru.nchernetsov.service.CommentService;

import java.util.List;
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
    public List<Comment> getAllComments() {
        return commentService.getAll();
    }

    @GetMapping(value = "/comments/books/{bookId}")
    public List<Comment> bookCommentsList(@PathVariable(value = "bookId") String bookId) {
        Book book = bookService.findOne(bookId);
        return book.getComments();
    }

    @PostMapping(value = "/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        comment.setId(UUID.randomUUID().toString());
        Comment createdComment = commentService.addCommentToBookById(comment.getBookId(), comment);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping(value = "/comments")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        Comment updatedComment = commentService.addCommentToBookById(comment.getBookId(), comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable(name = "commentId") String commentId) {
        Comment comment = commentService.deleteCommentById(commentId);
        return ResponseEntity.ok(comment);
    }
}
