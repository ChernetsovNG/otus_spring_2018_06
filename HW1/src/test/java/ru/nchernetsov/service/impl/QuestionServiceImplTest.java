package ru.nchernetsov.service.impl;

import org.junit.jupiter.api.Test;
import ru.nchernetsov.domain.Question;
import ru.nchernetsov.service.QuestionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceImplTest {

    private QuestionService questionService = new QuestionServiceImpl();

    @Test
    void getQuestionsTest() {
        List<Question> questions = questionService.getQuestions("classpath:test-file.csv");

        assertEquals(3, questions.size());

        assertEquals("2 + 2?", questions.get(0).getText());
        assertEquals("Факториал 3", questions.get(1).getText());
        assertEquals("Корень из 121", questions.get(2).getText());

        assertEquals(Integer.valueOf(4), questions.get(0).getRightAnswersNumbers().get(0));
        assertEquals(Integer.valueOf(2), questions.get(1).getRightAnswersNumbers().get(0));
        assertEquals(Integer.valueOf(3), questions.get(2).getRightAnswersNumbers().get(0));
    }
}
