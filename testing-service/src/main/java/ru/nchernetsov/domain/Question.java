package ru.nchernetsov.domain;

import lombok.Data;

import java.util.List;

/**
 * Тестовый вопрос
 */
@Data
public class Question {
    /**
     * Идентификатор
     */
    private final int id;
    /**
     * Текст вопроса
     */
    private final String text;
    /**
     * Количетво вариантов ответа
     */
    private final int answersCount;
    /**
     * Варианты ответа
     */
    private final List<String> answersVariants;
    /**
     * Номера правильных ответов
     */
    private final List<Integer> rightAnswersNumbers;
}
