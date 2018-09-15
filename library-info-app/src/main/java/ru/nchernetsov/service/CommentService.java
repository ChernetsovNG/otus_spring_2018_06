package ru.nchernetsov.service;

import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;

import java.util.List;

public interface CommentService {

    void addCommentToBookByTitle(String bookTitle, Comment comment);

    Comment addCommentToBookById(String bookId, Comment comment);

    List<Comment> getBookComments(String bookId);

    Comment createOrUpdateComment(Comment comment);

    Comment deleteCommentById(String id);

    Book getBookByCommentId(String commentId);

    List<Comment> getCommentsByIds(List<String> ids);

    List<Comment> getAll();
}
