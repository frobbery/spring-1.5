package com.example.spring_111.service.question;

import com.example.spring_111.config.QuestionConfig;
import com.example.spring_111.dao.QuestionDao;
import com.example.spring_111.domain.QuestionWithAnswers;
import com.example.spring_111.service.input_output.InputOutputService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final QuestionConfig questionConfig;
    private final InputOutputService inputOutputService;

    public QuestionServiceImpl(QuestionDao questionDao,
                               QuestionConfig questionConfig,
                               InputOutputService inputOutputService) throws IOException, URISyntaxException {
        this.questionDao = questionDao;
        questionDao.addQuestionsFromFile();
        this.questionConfig = questionConfig;
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void printAllQuestionsWithAnswers(Locale locale) {
        inputOutputService.printAllQuestions(questionDao.getAllQuestions(), locale);
    }

    @Override
    public Boolean conductTesting(Locale locale) {
        inputOutputService.setLocale(locale);
        var studentName = inputOutputService.getStudentLastAndFirstName();
        var questions = questionDao.getAllQuestions();
        int numberOfPoints = 0;
        for (QuestionWithAnswers question : questions) {
            var answer = inputOutputService.printQuestionAndGetAnswer(question);
            var answerIsRight = question.answerIsRight(answer, locale);
            inputOutputService.printAnswerResult(answerIsRight);
            if (answerIsRight) {
                numberOfPoints++;
            }
        }
        inputOutputService.printResultOfStudent(studentName);
        var testPassed = numberOfPoints >= questionConfig.getNumber();
        inputOutputService.printTestResult(testPassed);
        return testPassed;
    }
}