package ru.nchernetsov.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.nchernetsov.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
