package ru.nchernetsov.repository;

import ru.nchernetsov.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Comment getById(long id);

    List<Comment> getAll();

    void insert(Comment comment);

}
