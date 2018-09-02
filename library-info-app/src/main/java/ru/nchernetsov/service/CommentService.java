package ru.nchernetsov.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Comment;

import java.util.List;

public interface CommentService {

    Mono<Comment> addCommentToBookByTitle(String bookTitle, Comment comment);

    Mono<Comment> addCommentToBookById(String bookId, Comment comment);

    List<Comment> getBookComments(String bookId);

    Mono<Comment> createOrUpdateComment(Comment comment);

    Mono<Void> deleteCommentById(String id);

    Mono<Book> getBookByCommentId(String commentId);

    List<Comment> getCommentsByIds(List<String> ids);

    Flux<Comment> getAll();
}
