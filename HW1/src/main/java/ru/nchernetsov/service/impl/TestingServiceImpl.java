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

    private boolean exit = false;

    public TestingServiceImpl(StudentDao studentDao, QuestionService questionService, ConsoleService consoleService) {
        this.studentDao = studentDao;
        this.questionService = questionService;
        this.consoleService = consoleService;
    }

    @Override
    public TestingResult performTestingProcess(String questionsFile) {
        consoleService.writeInConsole("Введите имя:");
        String firstName = consoleService.readFromConsole();
        consoleService.writeInConsole("Введите фамилию:");
        String lastName = consoleService.readFromConsole();

        clearState();

        Student student = studentDao.findByName(firstName, lastName);
        List<Question> questions = questionService.getQuestions(questionsFile);

        consoleService.writeInConsole("Начинаем тестирование! В любой момент введите exit, чтобы выйти");
        for (Question question : questions) {
            if (!exit) {
                testingOneQuestion(question);
            } else {
                consoleService.writeInConsole("Выходим из тестирования по команде exit!");
                return null;
            }
        }

        TestingResult testingResult = new TestingResult(student, questionIds, chooseAnswers, rightAnswers, rightAnswersCount);

        consoleService.writeInConsole("Уважаемый " + student.getFirstName() + " " + student.getLastName() +
            "! По результатам прохождения теста вы набрали " + rightAnswersCount + " баллов из " + questionIds.size());
        consoleService.writeInConsole("Сводка по результатам теста:  " + testingResult);

        return testingResult;
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
            exit = true;
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

    private void clearState() {
        questionIds.clear();
        chooseAnswers.clear();
        rightAnswers.clear();
        rightAnswersCount = 0;
    }

    private static <T> boolean listEquals(List<T> listA, List<T> listB) {
        return listA.containsAll(listB) && listB.containsAll(listA);
    }
}
