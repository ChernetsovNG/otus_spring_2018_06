package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.service.CommentService;

import java.util.ArrayList;
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
    public Comment addCommentToBookById(String bookId, Comment comment) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.ifPresent(book -> addCommentToBook(comment, book));
        return comment;
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
    public Comment deleteCommentById(String id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            String bookId = comment.getBookId();
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                book.removeComment(comment.getText());
                commentRepository.delete(comment);
                bookRepository.save(book);
            }
            return comment;
        }
        return null;
    }

    @Override
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
    public List<Comment> getCommentsByIds(List<String> ids) {
        Iterable<Comment> comments = commentRepository.findAllById(ids);
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            commentList.add(comment);
        }
        return commentList;
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    private void addCommentToBook(Comment comment, Book book) {
        comment.setBookId(book.getId());
        commentRepository.save(comment);
        book.addComment(comment);
        bookRepository.save(book);
    }

}
