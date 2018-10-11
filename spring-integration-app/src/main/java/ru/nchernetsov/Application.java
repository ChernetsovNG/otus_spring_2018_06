package ru.nchernetsov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import ru.nchernetsov.domain.Person;
import ru.nchernetsov.integration.PrinterGateway;

@SpringBootApplication
@Configuration
@ImportResource("integration-context.xml")
public class Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        /*Person[] persons = {new Person("Nikita", "Chernetsov"), new Person("John", "Snow"),
                new Person("Jane", "Dou")};

        for (Person person : persons) {
            Message<?> message = MessageBuilder
                    .withPayload(person)
                    .build();

            gateway.print(message);
        }*/
    }

}
