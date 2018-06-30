package ru.nchernetsov.service;

import ru.nchernetsov.domain.TestingResult;

public interface TestingService {
    /**
     * Провести процесс тестирования
     *
     * @param questionsFile - файл с вопросами теста
     * @return - результаты прохождения теста
     */
    TestingResult performTestingProcess(String questionsFile);
}
