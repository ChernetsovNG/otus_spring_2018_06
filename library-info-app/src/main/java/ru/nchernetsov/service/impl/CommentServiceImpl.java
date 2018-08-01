package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.service.CommentService;

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
    public void addCommentToBook(String bookTitle, String commentText) {
        Optional<Book> bookOptional = bookRepository.findByTitle(bookTitle);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            Comment comment = new Comment(commentText);

            comment.setBook(book);

            commentRepository.save(comment);

            book.addComment(comment);

            bookRepository.save(book);
        }
    }

}
