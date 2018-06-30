package ru.nchernetsov.service;

import ru.nchernetsov.domain.TestingResult;

public interface TestingService {
    /**
     * Провести процесс тестирования
     *
     * @return - результаты прохождения теста
     */
    TestingResult performTestingProcess();
}
