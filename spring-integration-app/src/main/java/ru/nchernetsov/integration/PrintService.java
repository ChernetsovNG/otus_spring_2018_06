package ru.nchernetsov.integration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

public class PrintService {

    public Message<?> print(Message<String> message) {
        MessageHeaders headers = message.getHeaders();

        System.out.println("Headers:");
        headers.forEach((key, value) -> System.out.println(key + " : " + value));

        System.out.println("Payload:");
        System.out.println(message.getPayload());

        return MessageBuilder.withPayload("New Message").build();
    }

}
