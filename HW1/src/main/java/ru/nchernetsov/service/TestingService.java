package ru.nchernetsov.service;

import ru.nchernetsov.domain.TestingResult;

public interface TestingService {
    /**
     * Провести процесс тестирования
     *
     * @param studentFirstName - имя студента
     * @param studentLastName  - фамилия студента
     * @param questionsFile    - файл с вопросами теста
     * @return - результаты прохождения теста
     */
    TestingResult performTestingProcess(String studentFirstName, String studentLastName, String questionsFile);
}
