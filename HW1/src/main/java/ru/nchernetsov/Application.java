package ru.nchernetsov;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.service.TestingService;

@ComponentScan
@Configuration
public class Application {

    @Value("${tests.folder}")
    private String testFilesFolder;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        TestingService testingService = context.getBean("testingService", TestingService.class);

        TestingResult testingResult = testingService.performTestingProcess();

        System.out.println(testingResult);
    }
}
