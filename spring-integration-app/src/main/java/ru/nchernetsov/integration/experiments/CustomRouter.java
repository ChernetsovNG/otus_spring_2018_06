package ru.nchernetsov.integration.experiments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.ArrayList;
import java.util.Collection;

public class CustomRouter extends AbstractMessageRouter {

    @Autowired
    @Qualifier(value = "intChannel")
    private MessageChannel intChannel;

    @Autowired
    @Qualifier(value = "stringChannel")
    private MessageChannel stringChannel;

    @Override
    protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
        Collection<MessageChannel> targetChannels = new ArrayList<>();
        if (message.getPayload().getClass().equals(Integer.class)) {
            targetChannels.add(intChannel);
        } else if (message.getPayload().getClass().equals(String.class)) {
            targetChannels.add(stringChannel);
        }
        return targetChannels;
    }

}
