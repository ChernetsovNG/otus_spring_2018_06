package ru.nchernetsov.integration;

import org.springframework.core.convert.converter.Converter;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Order;

import java.util.Random;

public class BookToOrderConverter implements Converter<Book, Order> {

    private final Random random = new Random();

    @Override
    public Order convert(Book book) {
        return new Order(book, random.nextBoolean());
    }

}
