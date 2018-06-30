package ru.nchernetsov.service;

import ru.nchernetsov.domain.Question;

import java.util.List;

public interface QuestionService {
    /**
     * Получить список вопросов из CSV-файла
     * @param pathToCSVFile - путь к CSV-файлу в папке ресурсов
     * @return - список вопросов
     */
    List<Question> getQuestions(String pathToCSVFile);
}
