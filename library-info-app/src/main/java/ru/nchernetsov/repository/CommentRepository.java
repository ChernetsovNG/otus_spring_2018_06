package ru.nchernetsov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nchernetsov.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
