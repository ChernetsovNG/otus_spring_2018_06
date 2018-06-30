package ru.nchernetsov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.service.ConsoleService;
import ru.nchernetsov.service.TestingService;

import java.io.FileNotFoundException;

public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        ConsoleService consoleService = context.getBean("consoleService", ConsoleService.class);
        TestingService testingService = context.getBean("testingService", TestingService.class);

        consoleService.writeInConsole("Введите имя:");
        String firstName = consoleService.readFromConsole();
        consoleService.writeInConsole("Введите фамилию:");
        String lastName = consoleService.readFromConsole();

        TestingResult testingResult = testingService.performTestingProcess(
                firstName, lastName, "classpath:tests/test-tasks-1.csv");
    }
}
