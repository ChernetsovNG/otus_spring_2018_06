package ru.nchernetsov.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nchernetsov.domain.Author;
import ru.nchernetsov.repository.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void removeByName(String name) {
        Author author = getByName(name);
        em.remove(author);
    }

}
