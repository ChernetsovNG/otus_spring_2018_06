package ru.nchernetsov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
