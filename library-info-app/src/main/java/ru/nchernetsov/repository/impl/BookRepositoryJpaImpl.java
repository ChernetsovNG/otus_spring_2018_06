package ru.nchernetsov.repository.impl;

import org.springframework.stereotype.Repository;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookRepositoryJpaImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Book getByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public void removeByTitle(String title) {
        Book book = getByTitle(title);
        em.remove(book);
    }
}
