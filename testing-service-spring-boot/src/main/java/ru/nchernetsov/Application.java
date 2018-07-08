package ru.nchernetsov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.service.TestingService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        TestingService testingService = context.getBean(TestingService.class);

        TestingResult testingResult = testingService.performTestingProcess();
        System.out.println(testingResult);
    }
}
