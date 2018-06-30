package ru.nchernetsov.domain;

import lombok.Data;

/**
 * Студент, проходящий тестирование
 */
@Data
public class Student {
    /**
     * Имя
     */
    private final String firstName;
    /**
     * Фамилия
     */
    private final String lastName;
}
