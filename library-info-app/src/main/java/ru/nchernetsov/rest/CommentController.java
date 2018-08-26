package ru.nchernetsov.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.service.BookService;
import ru.nchernetsov.service.CommentService;

import java.util.List;

@RestController
@CrossOrigin
public class CommentController {

    private final BookService bookService;

    private final CommentService commentService;

    public CommentController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/comments/books/{bookId}")
    public List<Comment> bookCommentsList(Model model, @PathVariable(value = "bookId") String bookId) {
        Book book = bookService.findOne(bookId);
        return book.getComments();
    }

    @PostMapping(value = "/comments")
    public ResponseEntity<?> addComment(String bookId, Comment comment) {
        commentService.addCommentToBookById(bookId, comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "commentId") String commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.ok().build();
    }
}
