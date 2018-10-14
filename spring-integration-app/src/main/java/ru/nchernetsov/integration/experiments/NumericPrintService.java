package ru.nchernetsov.integration.experiments;

import org.springframework.messaging.Message;

public class NumericPrintService {

    public void print(Message<String> message) {
        System.out.println("Printing the numeric: " + message.getPayload());
    }

}
