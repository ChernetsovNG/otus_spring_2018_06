package ru.nchernetsov.integration.experiments;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

public class CustomChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return MessageBuilder
            .withPayload(message.getPayload().toString() + " message intercepted")
            .build();
    }

}
