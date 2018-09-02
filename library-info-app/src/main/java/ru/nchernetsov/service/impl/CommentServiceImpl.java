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
    public Mono<Comment> addCommentToBookByTitle(String bookTitle, Comment comment) {
        Mono<Book> bookMono = bookRepository.findByTitle(bookTitle);
        return bookMono.map(book -> addCommentToBook(comment, book)).flatMap(Mono::single);
    }

    @Override
    public Mono<Comment> addCommentToBookById(String bookId, Comment comment) {
        Mono<Book> bookMono = bookRepository.findById(bookId);
        return bookMono.map(book -> addCommentToBook(comment, book)).flatMap(Mono::single);
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
    public Mono<Void> deleteCommentById(String id) {
        Mono<Comment> commentMono = commentRepository.findById(id);
        return commentMono.map(comment -> {
            String bookId = comment.getBookId();
            Mono<Book> bookMono = bookRepository.findById(bookId);
            bookMono.subscribe(book -> {
                book.removeComment(comment.getText());
                bookRepository.save(book).subscribe();
            });
            return commentRepository.delete(comment);
        }).flatMap(Mono::single);
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

    private Mono<Comment> addCommentToBook(Comment comment, Book book) {
        comment.setBookId(book.getId());
        book.addComment(comment);
        bookRepository.save(book).subscribe();
        return commentRepository.save(comment);
    }

}
