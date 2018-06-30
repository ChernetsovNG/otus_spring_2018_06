package ru.nchernetsov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
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

import static ru.nchernetsov.utils.Utils.getFileNamesFromResourceFolder;
import static ru.nchernetsov.utils.Utils.listEquals;

@Slf4j
@Service("testingService")
@PropertySource("classpath:application.properties")
public class TestingServiceImpl implements TestingService {

    private final StudentDao studentDao;

    private final QuestionService questionService;

    private final ConsoleService consoleService;

    private final List<Integer> questionIds = new ArrayList<>();
    private final List<List<Integer>> chooseAnswers = new ArrayList<>();
    private final List<List<Integer>> rightAnswers = new ArrayList<>();
    private int rightAnswersCount = 0;

    private boolean exit = false;

    @Value("${tests.folder}")
    private String testFilesFolder;

    TestingServiceImpl(StudentDao studentDao, QuestionService questionService, ConsoleService consoleService) {
        this.studentDao = studentDao;
        this.questionService = questionService;
        this.consoleService = consoleService;
    }

    @Override
    public TestingResult performTestingProcess() {
        Student student = readStudentName();
        List<Question> questions = readTestFileQuestions();
        TestingResult testingResult = testAllQuestions(student, questions);

        consoleService.writeInConsole("Уважаемый " + student.getFirstName() + " " + student.getLastName() +
            "! По результатам прохождения теста вы набрали " + rightAnswersCount + " баллов из " + questionIds.size());
        consoleService.writeInConsole("Сводка по результатам теста:  " + testingResult);

        return testingResult;
    }

    private Student readStudentName() {
        consoleService.writeInConsole("Введите имя:");
        String firstName = consoleService.readFromConsole();
        consoleService.writeInConsole("Введите фамилию:");
        String lastName = consoleService.readFromConsole();
        return studentDao.findByName(firstName, lastName);
    }

    private List<Question> readTestFileQuestions() {
        consoleService.writeInConsole("Выберите файл для тестирования из списка. Введите номер выбранного файла:");
        List<String> testFileNamesList = getFileNamesFromResourceFolder(testFilesFolder);
        testFileNamesList.sort(String::compareTo);
        for (int i = 0; i < testFileNamesList.size(); i++) {
            consoleService.writeInConsole((i + 1) + " : " + testFileNamesList.get(i));
        }
        int selectedTestFileIndex = Integer.parseInt(consoleService.readFromConsole());
        if (selectedTestFileIndex < 1 || selectedTestFileIndex > testFileNamesList.size()) {
            throw new IndexOutOfBoundsException("Вы ввели номер несуществующего тестового файла!");
        }
        String questionFileName = "classpath:" + testFilesFolder + "/" + testFileNamesList.get(selectedTestFileIndex - 1);
        return questionService.getQuestions(questionFileName);
    }

    private TestingResult testAllQuestions(Student student, List<Question> questions) {
        clearState();
        consoleService.writeInConsole("Начинаем тестирование! В любой момент введите exit, чтобы выйти");
        for (Question question : questions) {
            if (!exit) {
                testingOneQuestion(question);
            } else {
                consoleService.writeInConsole("Выходим из тестирования по команде exit!");
                return null;
            }
        }
        return new TestingResult(student, questionIds, chooseAnswers, rightAnswers, rightAnswersCount);
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
}
