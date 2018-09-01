package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.service.CommentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addCommentToBookByTitle(String bookTitle, Comment comment) {
        Mono<Book> bookOptional = bookRepository.findByTitle(bookTitle);
        bookOptional.subscribe(book -> addCommentToBook(comment, book));
    }

    @Override
    public Comment addCommentToBookById(String bookId, Comment comment) {
        Mono<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.subscribe(book -> addCommentToBook(comment, book));
        return comment;
    }

    @Override
    public List<Comment> getBookComments(String bookId) {
        Mono<Book> bookOptional = bookRepository.findById(bookId);

        List<Comment> bookComments = new ArrayList<>();

        bookOptional.subscribe(book -> bookComments.addAll(book.getComments()));
        return bookComments;
    }

    @Override
    public Mono<Comment> createOrUpdateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteCommentById(String id) {
        Mono<Comment> commentMono = commentRepository.findById(id);
        Comment comment = commentMono.block();
        if (comment != null) {
            String bookId = comment.getBookId();
            Mono<Book> bookOptional = bookRepository.findById(bookId);
            bookOptional.subscribe(book -> {
                book.removeComment(comment.getText());
                commentRepository.delete(comment);
                bookRepository.save(book);
            });
        }
        return comment;
    }

    @Override
    public Mono<Book> getBookByCommentId(String commentId) {
        Mono<Comment> commentMono = commentRepository.findById(commentId);
        return commentMono.map(comment -> bookRepository.findById(comment.getBookId())).block();
    }

    @Override
    public List<Comment> getCommentsByIds(List<String> ids) {
        Flux<Comment> comments = commentRepository.findAllById(ids);
        List<Comment> commentList = new ArrayList<>();

        comments.subscribe(commentList::add);

        return commentList;
    }

    @Override
    public Flux<Comment> getAll() {
        return commentRepository.findAll();
    }

    private void addCommentToBook(Comment comment, Book book) {
        comment.setBookId(book.getId());
        commentRepository.save(comment).subscribe();
        book.addComment(comment);
        bookRepository.save(book).subscribe();
    }

}
