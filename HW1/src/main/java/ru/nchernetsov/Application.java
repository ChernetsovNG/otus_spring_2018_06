package ru.nchernetsov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.service.ConsoleService;
import ru.nchernetsov.service.TestingService;

import java.io.FileNotFoundException;

public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        TestingService testingService = context.getBean("testingService", TestingService.class);

        TestingResult testingResult = testingService.performTestingProcess("classpath:tests/test-tasks-1.csv");

        System.out.println(testingResult);
    }
}
