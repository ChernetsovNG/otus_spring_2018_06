package ru.nchernetsov.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nchernetsov.domain.mongodb.Comment;

public interface MongoDBCommentRepository extends MongoRepository<Comment, String> {
}
