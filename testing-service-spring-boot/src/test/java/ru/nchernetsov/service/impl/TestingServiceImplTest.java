package ru.nchernetsov.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nchernetsov.TestApplicationRunner;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.repository.StudentDao;
import ru.nchernetsov.repository.StudentDaoMock;
import ru.nchernetsov.service.ConsoleService;
import ru.nchernetsov.service.QuestionService;
import ru.nchernetsov.service.TestingService;
import ru.nchernetsov.service.config.LocaleSettingsLoader;
import ru.nchernetsov.service.config.TestsSettingsLoader;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(TestApplicationRunner.class)
public class TestingServiceImplTest {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TestsSettingsLoader testsSettingsLoader;

    @Autowired
    private LocaleSettingsLoader localeSettingsLoader;

    @Test
    public void performTestingProcessTest() {
        // Имитируем последовательный ввод пользователя в консоль
        // имя, фамилия, номер файла, 5 ответов
        ByteArrayInputStream in = new ByteArrayInputStream("Nikita\nChernetsov\n2\n3\n4\n1,2\n2\n3".getBytes());
        System.setIn(in);

        StudentDao studentDao = new StudentDaoMock();
        QuestionService questionService = new QuestionServiceImpl();
        ConsoleService consoleService = new ConsoleServiceImpl();

        TestingService testingService = new TestingServiceImpl(studentDao, questionService, consoleService,
            messageSource, testsSettingsLoader, localeSettingsLoader);

        TestingResult testingResult = testingService.performTestingProcess();

        System.setIn(System.in);

        assertEquals("Nikita", testingResult.getStudent().getFirstName());
        assertEquals("Chernetsov", testingResult.getStudent().getLastName());

        assertEquals(4, testingResult.getRightAnswersCount());

        assertEquals(Arrays.asList(
            Collections.singletonList(3),
            Collections.singletonList(4),
            Arrays.asList(1, 2),
            Collections.singletonList(2),
            Collections.singletonList(3)),
            testingResult.getChooseAnswers());

        assertEquals(80, testingResult.rightAnswersPercent());
    }
}
