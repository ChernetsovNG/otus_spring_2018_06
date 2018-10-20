package ru.nchernetsov.integration.experiments;

import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomSplitter extends AbstractMessageSplitter {

    @Override
    protected Object splitMessage(Message<?> message) {
        return new ArrayList<>(Arrays.asList(message.getPayload().toString().split(" ")));
    }

}
