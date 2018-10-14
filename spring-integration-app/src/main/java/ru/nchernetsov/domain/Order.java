package ru.nchernetsov.domain;

import java.util.UUID;

/**
 * Условный класс без особого физического смысла, просто для пробы Spring Integration
 */
public class Order {

    private final UUID id = UUID.randomUUID();

    private final Book book;

    private final boolean premium;

    private boolean processed = false;

    public Order(Book book, boolean premium) {
        this.book = book;
        this.premium = premium;
    }

    public UUID getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public boolean isPremium() {
        return premium;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", book=" + book +
            ", premium=" + premium +
            '}';
    }

}
