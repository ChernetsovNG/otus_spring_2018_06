package ru.nchernetsov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.nchernetsov.dao.StudentDao;
import ru.nchernetsov.domain.Question;
import ru.nchernetsov.domain.Student;
import ru.nchernetsov.domain.TestingResult;
import ru.nchernetsov.service.ConsoleService;
import ru.nchernetsov.service.QuestionService;
import ru.nchernetsov.service.TestingService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.nchernetsov.utils.Constants.*;
import static ru.nchernetsov.utils.Utils.getFileNamesFromResourceFolder;
import static ru.nchernetsov.utils.Utils.listEquals;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class TestingServiceImpl implements TestingService {

    private final StudentDao studentDao;

    private final QuestionService questionService;

    private final ConsoleService consoleService;

    private final MessageSource messageSource;

    @Value("${tests.folder}")
    private String testFilesFolder;

    @Value("${test.threshold}")
    private int testThreshold;

    @Value("${locale}")
    private String chosenLocale;

    private Locale locale;

    @Autowired
    TestingServiceImpl(StudentDao studentDao, QuestionService questionService, ConsoleService consoleService, MessageSource messageSource) {
        this.studentDao = studentDao;
        this.questionService = questionService;
        this.consoleService = consoleService;
        this.messageSource = messageSource;
    }

    @Override
    public TestingResult performTestingProcess() {
        setLocale();

        Student student = getStudentByName();
        List<Question> questions = readTestFileQuestions();
        TestingResult testingResult = testAllQuestions(student, questions);

        consoleService.writeInConsole(getMessage("respected") + SPACE + student.getFirstName() + SPACE + student.getLastName() + EXCLAMATION_POINT + SPACE +
                getMessage("tests.results") + SPACE + testingResult.getRightAnswersCount() + SPACE +
                getMessage("points") + SPACE + getMessage("from") + SPACE + testingResult.getQuestionIds().size());

        if (testingResult.rightAnswersPercent() >= testThreshold) {
            consoleService.writeInConsole(getMessage("correct.answers.percent") + COLON + SPACE +
                    testingResult.rightAnswersPercent() + " >= " + testThreshold + DOT + SPACE + getMessage("test.passed") + EXCLAMATION_POINT);
        } else {
            consoleService.writeInConsole(getMessage("correct.answers.percent") + COLON + SPACE +
                    testingResult.rightAnswersPercent() + " >= " + testThreshold + DOT + SPACE + getMessage("test.failed") + EXCLAMATION_POINT);
        }
        consoleService.writeInConsole(getMessage("test.summary") + COLON + SPACE + testingResult);

        return testingResult;
    }

    private Student getStudentByName() {
        consoleService.writeInConsole(getMessage("enter.first.name") + COLON);
        String firstName = consoleService.readFromConsole();
        if (firstName.equalsIgnoreCase("exit")) {
            exit();
        }
        consoleService.writeInConsole(getMessage("enter.last.name") + COLON);
        String lastName = consoleService.readFromConsole();
        if (lastName.equalsIgnoreCase("exit")) {
            exit();
        }
        return studentDao.findByName(firstName, lastName);
    }

    private List<Question> readTestFileQuestions() {
        consoleService.writeInConsole(getMessage("select.file") + COLON);
        List<String> testFileNamesList = getFileNamesFromResourceFolder(testFilesFolder + "/" + locale);
        testFileNamesList.sort(String::compareTo);
        for (int i = 0; i < testFileNamesList.size(); i++) {
            consoleService.writeInConsole((i + 1) + " : " + testFileNamesList.get(i));
        }
        String userInput = consoleService.readFromConsole();
        if (userInput.equalsIgnoreCase("exit")) {
            exit();
        }
        int selectedTestFileIndex = Integer.parseInt(userInput);
        if (selectedTestFileIndex < 1 || selectedTestFileIndex > testFileNamesList.size()) {
            throw new IndexOutOfBoundsException(getMessage("incorrect.file.number") + EXCLAMATION_POINT);
        }
        String questionFileName = getClasspathFile(testFileNamesList.get(selectedTestFileIndex - 1));
        try {
            return questionService.getQuestions(questionFileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    private TestingResult testAllQuestions(Student student, List<Question> questions) {
        consoleService.writeInConsole(getMessage("start.testing"));

        final List<Integer> questionIds = new ArrayList<>();
        final List<List<Integer>> chooseAnswers = new ArrayList<>();
        final List<List<Integer>> rightAnswers = new ArrayList<>();
        int rightAnswersCount = 0;

        for (Question question : questions) {
            boolean isItRight = testingOneQuestion(question, questionIds, chooseAnswers, rightAnswers);
            if (isItRight) {
                rightAnswersCount++;
            }
        }

        return new TestingResult(student, questionIds, chooseAnswers, rightAnswers, rightAnswersCount);
    }

    private boolean testingOneQuestion(Question question, List<Integer> questionIds, List<List<Integer>> chooseAnswers,
                                       List<List<Integer>> rightAnswers) {
        consoleService.writeInConsole(getMessage("question.number") + SPACE + question.getId());
        consoleService.writeInConsole(question.getText());
        for (int i = 0; i < question.getAnswersCount(); i++) {
            consoleService.writeInConsole(getMessage("option.number") + SPACE + (i + 1) + COLON);
            consoleService.writeInConsole(question.getAnswersVariants().get(i));
        }
        consoleService.writeInConsole(getMessage("select.correct.answers") + DOT + SPACE +
                getMessage("enter.selected.answers"));
        String studentAnswersString = consoleService.readFromConsole();
        if (studentAnswersString.equalsIgnoreCase("exit")) {
            exit();
        }
        List<Integer> studentAnswers = Arrays.stream(studentAnswersString.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        consoleService.writeInConsole(getMessage("you.choose") + COLON + SPACE + studentAnswers);
        consoleService.writeInConsole(getMessage("right.answers") + COLON + SPACE + question.getRightAnswersNumbers());
        questionIds.add(question.getId());
        chooseAnswers.add(studentAnswers);
        rightAnswers.add(question.getRightAnswersNumbers());
        if (listEquals(studentAnswers, question.getRightAnswersNumbers())) {
            consoleService.writeInConsole(getMessage("correct.answer"));
            return true;
        } else {
            consoleService.writeInConsole(getMessage("incorrect.answer"));
            return false;
        }
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, new String[]{}, locale);
    }

    private void setLocale() {
        switch (chosenLocale) {
            case "en":
                locale = Locale.ENGLISH;
                break;
            case "ru":
                locale = new Locale("ru");
                break;
            default:
                throw new UnsupportedOperationException("Locale " + chosenLocale + " is not supported");
        }
    }

    private void exit() {
        consoleService.writeInConsole(getMessage("exit.test.by.user") + EXCLAMATION_POINT);
        System.exit(0);
    }

    private String getClasspathFile(String fileName) {
        return "classpath:" + testFilesFolder + FILE_SEPARATOR + locale + FILE_SEPARATOR + fileName;
    }

}
