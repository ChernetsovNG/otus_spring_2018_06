package ru.nchernetsov.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.nchernetsov.dao.StudentDao;
import ru.nchernetsov.domain.Question;
import ru.nchernetsov.domain.Student;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.service.ConsoleService;
import ru.nchernetsov.service.QuestionService;
import ru.nchernetsov.service.TestingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class TestingServiceImpl implements TestingService {

    private final StudentDao studentDao;

    private final QuestionService questionService;

    private final ConsoleService consoleService;

    private final List<Integer> questionIds = new ArrayList<>();
    private final List<List<Integer>> chooseAnswers = new ArrayList<>();
    private final List<List<Integer>> rightAnswers = new ArrayList<>();
    private int rightAnswersCount = 0;

    public TestingServiceImpl(StudentDao studentDao, QuestionService questionService, ConsoleService consoleService) {
        this.studentDao = studentDao;
        this.questionService = questionService;
        this.consoleService = consoleService;
    }

    @Override
    public TestingResult performTestingProcess(String studentFirstName, String studentLastName, String questionsFile) {
        Optional<Student> studentOptional = studentDao.findByName(studentFirstName, studentLastName);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<Question> questions = questionService.getQuestions(questionsFile);
            if (questions.size() > 0) {

                questionIds.clear();
                chooseAnswers.clear();
                rightAnswers.clear();
                rightAnswersCount = 0;

                consoleService.writeInConsole("Начинаем тестирование! В любой момент введите exit, чтобы выйти");
                for (Question question : questions) {
                    testingOneQuestion(question);
                }
                TestingResult testingResult = new TestingResult(questionIds, chooseAnswers, rightAnswers, rightAnswersCount);

                consoleService.writeInConsole("Уважаемый " + student.getFirstName() + " " + student.getLastName() +
                        "! По результатам прохождения теста вы набрали " + rightAnswersCount + " баллов из " + questionIds.size());
                consoleService.writeInConsole("Сводка по результатам теста:  " + testingResult);

                return testingResult;
            } else {
                log.warn("File {} doesn't contains questions! Return null testing results");
            }
        } else {
            log.warn("Student: {}, {} not found. Return null testing result", studentFirstName, studentLastName);
        }
        return null;
    }

    private void testingOneQuestion(Question question) {
        consoleService.writeInConsole("Вопрос номер " + question.getId());
        consoleService.writeInConsole(question.getText());
        for (int i = 0; i < question.getAnswersCount(); i++) {
            consoleService.writeInConsole("Вариант номер " + (i + 1) + ":");
            consoleService.writeInConsole(question.getAnswersVariants().get(i));
        }
        consoleService.writeInConsole("Выберите один или несколько правильных ответов. " +
                "Введите номера выбранных ответов (отсчёт от 1) в консоль через запятую");
        String studentAnswersString = consoleService.readFromConsole();
        if (studentAnswersString.equalsIgnoreCase("exit")) {
            return;
        }
        List<Integer> studentAnswers = Arrays.stream(studentAnswersString.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        consoleService.writeInConsole("Вы выбрали варианты ответов: " + studentAnswers);
        consoleService.writeInConsole("Правильные ответы: " + question.getRightAnswersNumbers());
        questionIds.add(question.getId());
        chooseAnswers.add(studentAnswers);
        rightAnswers.add(question.getRightAnswersNumbers());
        if (listEquals(studentAnswers, question.getRightAnswersNumbers())) {
            rightAnswersCount++;
            consoleService.writeInConsole("Вы ответили правильно на этот вопрос");
        } else {
            consoleService.writeInConsole("Вы ответили неправильно на этот вопрос");
        }
    }

    private <T> boolean listEquals(List<T> listA, List<T> listB) {
        return listA.containsAll(listB) && listB.containsAll(listA);
    }
}
