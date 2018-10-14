package ru.nchernetsov.integration.experiments;

import org.springframework.messaging.Message;

public class CustomTransformer {

    public String transform(Message<String> message) {
        String[] tokens = message.getPayload().split(" ");
        return String.join(", ", tokens);
    }

}
