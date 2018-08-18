package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.service.CommentService;

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
    public void addCommentToBookByTitle(String bookTitle, Comment comment) {
        Optional<Book> bookOptional = bookRepository.findByTitle(bookTitle);

        bookOptional.ifPresent(book -> addCommentToBook(comment, book));
    }

    @Override
    public void addCommentToBookById(String bookId, Comment comment) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.ifPresent(book -> addCommentToBook(comment, book));
    }

    @Override
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
    public Comment createOrUpdateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(String id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            String bookId = comment.getBook();
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                book.removeComment(comment.getComment());
                commentRepository.delete(comment);
                bookRepository.save(book);
            }
        }
    }

    @Override
    public Book getBookByCommentId(String commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            String bookId = comment.getBook();
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                return bookOptional.get();
            }
        }
        return null;
    }

    private void addCommentToBook(Comment comment, Book book) {
        comment.setBook(book);
        commentRepository.save(comment);
        book.addComment(comment);
        bookRepository.save(book);
    }

}
