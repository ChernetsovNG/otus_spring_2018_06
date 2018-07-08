package ru.nchernetsov.service.impl;

import org.junit.Test;
import ru.nchernetsov.domain.Question;
import ru.nchernetsov.service.QuestionService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.nchernetsov.utils.Constants.FILE_SEPARATOR;

public class QuestionServiceImplTest {

    private final QuestionService questionService = new QuestionServiceImpl();

    @Test
    public void getQuestionsTest() throws IOException {
        String pathToCSVFile = "classpath:tests" + FILE_SEPARATOR + "ru" + FILE_SEPARATOR + "test-file.csv";

        List<Question> questions = questionService.getQuestions(pathToCSVFile);

        assertEquals(3, questions.size());

        assertEquals("2 + 2?", questions.get(0).getText());
        assertEquals("Факториал 3?", questions.get(1).getText());
        assertEquals("Корень из 121?", questions.get(2).getText());

        assertEquals(Integer.valueOf(4), questions.get(0).getRightAnswersNumbers().get(0));
        assertEquals(Integer.valueOf(2), questions.get(1).getRightAnswersNumbers().get(0));
        assertEquals(Integer.valueOf(3), questions.get(2).getRightAnswersNumbers().get(0));
    }

    @Test(expected = FileNotFoundException.class)
    public void badFileNameShouldThrowFileNotFoundException() throws IOException {
        questionService.getQuestions("no-exists-file.csv");
    }

    @Test(expected = IllegalStateException.class)
    public void fileWithNoQuestionsShouldThrowIllegalStateException() throws IOException {
        questionService.getQuestions("classpath:tests/ru/file-without-questions.csv");
    }
}
