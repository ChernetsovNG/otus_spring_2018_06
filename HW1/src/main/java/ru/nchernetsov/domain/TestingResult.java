package ru.nchernetsov.domain;

import lombok.Data;

import java.util.List;

/**
 * Результаты тестирования
 */
@Data
public class TestingResult {
    /**
     * Тестируемый студент
     */
    private final Student student;
    /**
     * Идентификаторы вопросов
     */
    private final List<Integer> questionIds;
    /**
     * Список выбранных вариантов ответов на каждый вопрос
     */
    private final List<List<Integer>> chooseAnswers;
    /**
     * Список правильных ответов на каждый вопрос
     */
    private final List<List<Integer>> rightAnswers;
    /**
     * Количество правильных ответов
     */
    private final int rightAnswersCount;
}
