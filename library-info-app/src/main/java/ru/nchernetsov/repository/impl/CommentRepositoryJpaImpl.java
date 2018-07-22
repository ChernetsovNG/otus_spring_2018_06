package ru.nchernetsov.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nchernetsov.domain.Comment;
import ru.nchernetsov.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryJpaImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public void insert(Comment comment) {
        em.persist(comment);
    }

}
