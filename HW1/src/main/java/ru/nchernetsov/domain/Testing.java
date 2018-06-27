package ru.nchernetsov.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для тестирования
 */
@Data
public class Testing {
    /**
     * Студент, проходящий тестирование
     */
    private final Student student;
    /**
     * Вопросы теста
     */
    private final List<Question> questions;
    /**
     * Список выбранных вариантов ответов на каждый вопрос
     */
    private List<List<Integer>> chooseAnswers = new ArrayList<>();
    /**
     * Количество правильных ответов
     */
    private int rightAnswersCount;
}
