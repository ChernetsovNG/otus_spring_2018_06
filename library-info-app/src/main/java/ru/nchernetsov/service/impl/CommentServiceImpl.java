package ru.nchernetsov.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.service.CommentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "comments", fallbackMethod = "addCommentToBookByTitleFallback")
    public void addCommentToBookByTitle(String bookTitle, Comment comment) {
        Optional<Book> bookOptional = bookRepository.findByTitle(bookTitle);
        bookOptional.ifPresent(book -> addCommentToBook(comment, book));
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "comments", fallbackMethod = "addCommentToBookByIdFallback")
    public Comment addCommentToBookById(String bookId, Comment comment) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.ifPresent(book -> addCommentToBook(comment, book));
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "comments", fallbackMethod = "getBookCommentsFallback")
    public List<Comment> getBookComments(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return book.getComments();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "comments", fallbackMethod = "createOrUpdateCommentFallback")
    public Comment createOrUpdateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "comments", fallbackMethod = "deleteCommentByIdFallback")
    public Comment deleteCommentById(String id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            String bookId = comment.getBookId();
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                book.removeComment(comment.getComment());
                commentRepository.delete(comment);
                bookRepository.save(book);
            }
            return comment;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "comments", fallbackMethod = "getBookByCommentIdFallback")
    public Book getBookByCommentId(String commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            String bookId = comment.getBookId();
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                return bookOptional.get();
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "comments", fallbackMethod = "getCommentsByIdsFallback")
    public List<Comment> getCommentsByIds(List<String> ids) {
        Iterable<Comment> comments = commentRepository.findAllById(ids);
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            commentList.add(comment);
        }
        return commentList;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "comments", fallbackMethod = "getAllFallback")
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    private void addCommentToBook(Comment comment, Book book) {
        comment.setBookId(book.getBookId());
        commentRepository.save(comment);
        book.addComment(comment);
        bookRepository.save(book);
    }

    // Fallback section

    public void addCommentToBookByTitleFallback(String bookTitle, Comment comment) {
    }

    public Comment addCommentToBookByIdFallback(String bookId, Comment comment) {
        return null;
    }

    public List<Comment> getBookCommentsFallback(String bookId) {
        return Collections.emptyList();
    }

    public Comment createOrUpdateCommentFallback(Comment comment) {
        return null;
    }

    public Comment deleteCommentByIdFallback(String id) {
        return null;
    }

    public Book getBookByCommentIdFallback(String commentId) {
        return null;
    }

    public List<Comment> getCommentsByIdsFallback(List<String> ids) {
        return Collections.emptyList();
    }

    public List<Comment> getAllFallback() {
        return Collections.emptyList();
    }
}
