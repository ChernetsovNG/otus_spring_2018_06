package ru.nchernetsov.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.service.BookService;
import ru.nchernetsov.service.CommentService;

@Controller
public class CommentController {

    private final BookService bookService;

    private final CommentService commentService;

    public CommentController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/comments/books/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String bookCommentsList(Model model, @PathVariable(value = "bookId") String bookId) {
        Book book = bookService.findOne(bookId);

        model.addAttribute("bookId", bookId);
        model.addAttribute("bookTitle", book.getTitle());
        model.addAttribute("comments", commentService.getBookComments(bookId));

        return "comments";
    }

    @GetMapping(value = "/comments/add/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String addCommentForm(Model model, @PathVariable(required = false, name = "bookId") String bookId) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("bookId", bookId);

        return "addComment";
    }

    @PostMapping(value = "/comments/add")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String addComment(Model model, String bookId, Comment comment) {
        commentService.addCommentToBookById(bookId, comment);

        model.addAttribute("bookTitle", bookService.findOne(bookId).getTitle());
        model.addAttribute("comments", commentService.getBookComments(bookId));

        return "redirect:/comments/books/" + bookId;
    }

    @GetMapping(value = "/comments/delete/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteComment(Model model, @PathVariable(name = "commentId") String commentId) {
        Book book = commentService.getBookByCommentId(commentId);

        commentService.deleteCommentById(commentId);

        model.addAttribute("bookTitle", book.getTitle());
        model.addAttribute("comments", commentService.getBookComments(book.getBookId()));

        return "redirect:/comments/books/" + book.getBookId();
    }
}
