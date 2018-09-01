package ru.nchernetsov.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;

import java.util.List;

public interface CommentService {

    void addCommentToBookByTitle(String bookTitle, Comment comment);

    Comment addCommentToBookById(String bookId, Comment comment);

    List<Comment> getBookComments(String bookId);

    Mono<Comment> createOrUpdateComment(Comment comment);

    Comment deleteCommentById(String id);

    Mono<Book> getBookByCommentId(String commentId);

    List<Comment> getCommentsByIds(List<String> ids);

    Flux<Comment> getAll();
}
