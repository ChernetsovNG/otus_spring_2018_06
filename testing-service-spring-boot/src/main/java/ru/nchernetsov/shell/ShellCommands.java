package ru.nchernetsov.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.exception.StopTestException;
import ru.nchernetsov.service.TestingService;

@ShellComponent
public class ShellCommands {

    private final TestingService testingService;

    @Autowired
    public ShellCommands(TestingService testingService) {
        this.testingService = testingService;
    }

    @ShellMethod("Add two integers together")
    public int add(int a, int b) {
        return a + b;
    }

    @ShellMethod("Start testing process")
    public TestingResult start() {
        try {
            return testingService.performTestingProcess();
        } catch (StopTestException e) {
            return null;
        }
    }

    @ShellMethod("Say hello.")
    public String greet(@ShellOption(defaultValue = "World") String who) {
        return "Hello " + who;
    }

}
