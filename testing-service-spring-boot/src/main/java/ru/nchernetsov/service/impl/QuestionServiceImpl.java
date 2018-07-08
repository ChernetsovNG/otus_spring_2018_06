package ru.nchernetsov.service.impl;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.nchernetsov.domain.Question;
import ru.nchernetsov.service.QuestionService;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Character CSV_FILE_DELIMITER = ';';

    @Override
    public List<Question> getQuestions(String pathToCSVFile) throws IOException {
        // обходим строки в CSV-файле
        List<Question> questions = readAllLinesFromCSVFile(pathToCSVFile).stream()
            .map(this::getQuestionFromCSVLine)
            .collect(Collectors.toList());
        if (questions.size() > 0) {
            return questions;
        } else {
            throw new IllegalStateException("File " + pathToCSVFile + " doesn't contains any test questions");
        }
    }

    // читаем и разбираем CSV-файл
    private List<String[]> readAllLinesFromCSVFile(String pathToCSVFile) throws IOException {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(ResourceUtils.getFile(pathToCSVFile)))
            .withCSVParser(new CSVParserBuilder()
                .withSeparator(CSV_FILE_DELIMITER)
                .build())
            .build();
        return csvReader.readAll();
    }

    // преобразуем строку в CSV-файле в вопрос для теста
    private Question getQuestionFromCSVLine(String[] csvLine) {
        int id = Integer.parseInt(csvLine[0].trim());
        String text = csvLine[1].trim();
        int answersCount = Integer.parseInt(csvLine[2].trim());
        List<String> answerVariants = new ArrayList<>();
        int index;
        for (index = 3; index < 3 + answersCount; index++) {
            answerVariants.add(csvLine[index].trim());
        }
        String rightAnswersString = csvLine[index];
        List<Integer> rightAnswersNumbers = Arrays.stream(rightAnswersString
            .substring(1, rightAnswersString.length() - 1).trim()
            .split(","))
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        return new Question(id, text, answersCount, answerVariants, rightAnswersNumbers);
    }
}
