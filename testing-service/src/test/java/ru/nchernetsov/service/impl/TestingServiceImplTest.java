package ru.nchernetsov.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.nchernetsov.dao.StudentDao;
import ru.nchernetsov.dao.StudentDaoMock;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.service.ConsoleService;
import ru.nchernetsov.service.QuestionService;
import ru.nchernetsov.service.TestingService;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestingServiceImplTest {

    @Test
    void performTestingProcessTest() {
        // Имитируем последовательный ввод пользователя в консоль
        // имя, фамилия, номер файла, 5 ответов
        ByteArrayInputStream in = new ByteArrayInputStream("Nikita\nChernetsov\n2\n3\n4\n1,2\n2\n3".getBytes());
        System.setIn(in);

        StudentDao studentDao = new StudentDaoMock();
        QuestionService questionService = new QuestionServiceImpl();
        ConsoleService consoleService = new ConsoleServiceImpl();

        TestingService testingService = new TestingServiceImpl(studentDao, questionService, consoleService, messageSource());

        // устанавливаем значение property для теста
        ReflectionTestUtils.setField(testingService, "testFilesFolder", "tests");
        ReflectionTestUtils.setField(testingService, "testThreshold", 75);
        ReflectionTestUtils.setField(testingService, "chosenLocale", "ru");

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

        assertEquals(80, testingResult.getRightAnswersPercent());
    }

    private MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
