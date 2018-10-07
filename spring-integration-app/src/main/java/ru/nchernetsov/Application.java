package ru.nchernetsov;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@Configuration
@ImportResource("integration-context.xml")
public class Application implements ApplicationRunner {

    private final DirectChannel inputChannel;

    private final MessagingTemplate template;

    public Application(@Qualifier(value = "inputChannel") DirectChannel inputChannel,
                       MessagingTemplate template) {
        this.inputChannel = inputChannel;
        this.template = template;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        Message<String> message = MessageBuilder
            .withPayload("Hello World")
            .setHeader("key", "value")
            .build();

        Message<?> returnMessage = template.sendAndReceive(inputChannel, message);

        System.out.println(returnMessage.getPayload());
    }

}
