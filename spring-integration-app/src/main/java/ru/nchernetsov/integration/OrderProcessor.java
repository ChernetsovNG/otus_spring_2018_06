package ru.nchernetsov.integration;

import ru.nchernetsov.domain.Order;

public class OrderProcessor {

    public Order process(Order order) {
        return doProcess(order, String.format("Processing order %s", order.getId()));
    }

    public Order fastProcess(Order order) {
        return doProcess(order, String.format("Fast processing order %s", order.getId()));
    }

    private Order doProcess(Order order, String message) {
        System.out.println(message);
        order.setProcessed(true);
        return order;
    }

}
