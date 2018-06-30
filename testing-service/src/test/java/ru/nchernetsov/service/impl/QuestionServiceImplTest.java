package ru.nchernetsov.service.impl;

import org.junit.jupiter.api.Test;
import ru.nchernetsov.domain.Question;
import ru.nchernetsov.service.QuestionService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionServiceImplTest {

    private final QuestionService questionService = new QuestionServiceImpl();

    @Test
    void getQuestionsTest() throws IOException {
        List<Question> questions = questionService.getQuestions("classpath:tests/ru/test-file.csv");

        assertEquals(3, questions.size());

        assertEquals("2 + 2?", questions.get(0).getText());
        assertEquals("Факториал 3?", questions.get(1).getText());
        assertEquals("Корень из 121?", questions.get(2).getText());

        assertEquals(Integer.valueOf(4), questions.get(0).getRightAnswersNumbers().get(0));
        assertEquals(Integer.valueOf(2), questions.get(1).getRightAnswersNumbers().get(0));
        assertEquals(Integer.valueOf(3), questions.get(2).getRightAnswersNumbers().get(0));
    }

    @Test
    void badFileNameShouldThrowFileNotFoundException() {
        assertThrows(FileNotFoundException.class, () -> questionService.getQuestions("no-exists-file.csv"));
    }

    @Test
    void fileWithNoQuestionsShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class,
            () -> questionService.getQuestions("classpath:tests/ru/file-without-questions.csv"));
    }
}
