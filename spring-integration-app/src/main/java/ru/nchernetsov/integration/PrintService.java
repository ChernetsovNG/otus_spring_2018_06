package ru.nchernetsov.integration;

import org.springframework.messaging.Message;

public class PrintService {

    public void print(Message<String> message) {
        System.out.println("Printing the string: " + message.getPayload());
    }

}
