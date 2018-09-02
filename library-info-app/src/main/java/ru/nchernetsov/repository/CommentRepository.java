package ru.nchernetsov.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.nchernetsov.domain.Comment;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
