package ru.nchernetsov.service.impl;

import org.springframework.stereotype.Service;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.BookRepository;
import ru.nchernetsov.repository.CommentRepository;
import ru.nchernetsov.service.CommentService;

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
        Book book = bookRepository.getByTitle(bookTitle);

        Comment comment = new Comment(commentText);

        commentRepository.insert(comment);

        book.addComment(comment);

        bookRepository.update(book);
    }
}
