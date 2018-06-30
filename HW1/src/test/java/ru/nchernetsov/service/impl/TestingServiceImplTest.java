package ru.nchernetsov.service.impl;

import org.junit.jupiter.api.Test;
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
        ByteArrayInputStream in = new ByteArrayInputStream("Nikita\nChernetsov\n3\n4\n1,2\n2\n3".getBytes());
        System.setIn(in);

        StudentDao studentDao = new StudentDaoMock();
        QuestionService questionService = new QuestionServiceImpl();
        ConsoleService consoleService = new ConsoleServiceImpl();

        TestingService testingService = new TestingServiceImpl(studentDao, questionService, consoleService);
        TestingResult testingResult = testingService.performTestingProcess("classpath:tests/math-test.csv");

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
    }
}