package ru.nchernetsov.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nchernetsov.domain.Genre;
import ru.nchernetsov.repository.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreRepositoryJpaImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g WHERE g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void removeByName(String name) {
        Genre genre = getByName(name);
        em.remove(genre);
    }

}
