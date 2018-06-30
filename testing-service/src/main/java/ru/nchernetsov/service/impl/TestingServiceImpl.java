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
@Service("testingService")
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

    private final List<Integer> questionIds = new ArrayList<>();
    private final List<List<Integer>> chooseAnswers = new ArrayList<>();
    private final List<List<Integer>> rightAnswers = new ArrayList<>();
    private int rightAnswersCount = 0;

    private boolean exit = false;

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
            getMessage("tests.results") + SPACE + rightAnswersCount + SPACE + getMessage("points") + SPACE + getMessage("from") + SPACE + questionIds.size());

        if (testingResult.getRightAnswersPercent() >= testThreshold) {
            consoleService.writeInConsole(getMessage("correct.answers.percent") + COLON + SPACE +
                testingResult.getRightAnswersPercent() + " >= " + testThreshold + DOT + SPACE + getMessage("test.passed") + EXCLAMATION_POINT);
        } else {
            consoleService.writeInConsole(getMessage("correct.answers.percent") + COLON + SPACE +
                testingResult.getRightAnswersPercent() + " >= " + testThreshold + DOT + SPACE + getMessage("test.failed") + EXCLAMATION_POINT);
        }
        consoleService.writeInConsole(getMessage("test.summary") + COLON + SPACE + testingResult);

        return testingResult;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, new String[]{}, locale);
    }

    private Student getStudentByName() {
        consoleService.writeInConsole(getMessage("enter.first.name") + COLON);
        String firstName = consoleService.readFromConsole();
        consoleService.writeInConsole(getMessage("enter.last.name") + COLON);
        String lastName = consoleService.readFromConsole();
        return studentDao.findByName(firstName, lastName);
    }

    private List<Question> readTestFileQuestions() {
        consoleService.writeInConsole(getMessage("select.file") + COLON);
        List<String> testFileNamesList = getFileNamesFromResourceFolder(testFilesFolder);
        testFileNamesList.sort(String::compareTo);
        for (int i = 0; i < testFileNamesList.size(); i++) {
            consoleService.writeInConsole((i + 1) + " : " + testFileNamesList.get(i));
        }
        int selectedTestFileIndex = Integer.parseInt(consoleService.readFromConsole());
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
        clearState();
        consoleService.writeInConsole(getMessage("start.testing"));
        for (Question question : questions) {
            if (!exit) {
                testingOneQuestion(question);
            } else {
                consoleService.writeInConsole(getMessage("exit.test.by.user") + EXCLAMATION_POINT);
                return null;
            }
        }
        int rightAnswersPercent = rightAnswersCount * 100 / questionIds.size();
        return new TestingResult(student, questionIds, chooseAnswers, rightAnswers, rightAnswersCount, rightAnswersPercent);
    }

    private void testingOneQuestion(Question question) {
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
            exit = true;
            return;
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
            rightAnswersCount++;
            consoleService.writeInConsole(getMessage("correct.answer"));
        } else {
            consoleService.writeInConsole(getMessage("incorrect.answer"));
        }
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

    private String getClasspathFile(String fileName) {
        return "classpath:" + testFilesFolder + "/" + fileName;
    }

    private void clearState() {
        questionIds.clear();
        chooseAnswers.clear();
        rightAnswers.clear();
        rightAnswersCount = 0;
    }
}
