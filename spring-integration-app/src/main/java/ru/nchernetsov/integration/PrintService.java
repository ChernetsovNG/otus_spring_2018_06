package ru.nchernetsov.integration;

import org.springframework.messaging.Message;

public class PrintService {

    public void print(Message<?> message) {
        System.out.println("Printing the string: " + message.getPayload());
    }

}
