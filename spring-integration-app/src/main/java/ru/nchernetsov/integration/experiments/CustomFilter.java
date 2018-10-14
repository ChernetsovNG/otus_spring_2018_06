package ru.nchernetsov.integration.experiments;

import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

public class CustomFilter implements MessageSelector {

    @Override
    public boolean accept(Message<?> message) {
        return message.getPayload().equals(7);
    }

}
