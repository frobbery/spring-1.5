package com.example.spring_111.service;

import com.example.spring_111.config.QuestionConfig;
import com.example.spring_111.dao.QuestionDao;
import com.example.spring_111.domain.QuestionWithAnswers;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.spring_111.util.QuestionWithAnswersUtil.getQuestionWithAnswersFromLine;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final QuestionConfig questionConfig;
    private final MessageSource messageSource;

    public QuestionServiceImpl(QuestionDao questionDao,
                               QuestionConfig questionConfig,
                               MessageSource messageSource) {
        this.questionDao = questionDao;
        this.questionConfig = questionConfig;
        this.messageSource = messageSource;
    }

    public void addQuestions() throws IOException, URISyntaxException {
        var uri = Objects.requireNonNull(getClass().getResource(questionConfig.getPath())).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri));
        List<Locale> supportedLocale = Arrays.stream(lines.get(0).split(",")).map(Locale::new)
                .collect(Collectors.toList());
        lines.stream().skip(1).forEach(line -> questionDao.addQuestion(getQuestionWithAnswersFromLine(line, supportedLocale)));

    }

    @Override
    public void printAllQuestionsWithAnswers(PrintStream printStream, Locale locale) {
        questionDao.getAllQuestions()
                .forEach(question -> printStream.println(question.getPrintedInLocale(locale)));
    }

    @Override
    public Boolean conductTesting(PrintStream printStream, Scanner scanner, Locale locale) {
        var messageUtil = new MessageUtil(messageSource, locale);
        var studentName = getStudentLastAndFirstName(printStream, scanner, messageUtil);
        var questions = questionDao.getAllQuestions();
        int numberOfPoints = 0;
        for (QuestionWithAnswers question : questions) {
            printStream.println(question.getPrintedInLocale(locale));
            var answer = scanner.nextLine();
            if (question.answerIsRight(answer, locale)) {
                numberOfPoints++;
                printStream.println(messageUtil.getMessageForRightAnswer());
            } else {
                printStream.println(messageUtil.getMessageForWrongAnswer());
            }
        }
        printStream.println(MessageFormat.format("{0} {1}:", messageUtil.getResultMessage(), studentName));
        if (numberOfPoints >= questionConfig.getNumber()) {
            printStream.println(messageUtil.getMessageForPassingTest());
            return true;
        } else {
            printStream.println(messageUtil.getMessageForFailingTest());
            return false;
        }
    }

    private String getStudentLastAndFirstName(PrintStream printStream, Scanner scanner, MessageUtil messageUtil) {
        printStream.println(messageUtil.getMessageAskingForFirstAndLastName());
        var firstAndLastName = scanner.nextLine();
        while (!firstAndLastName.contains(" ") && firstAndLastName.split(" ").length != 2) {
            printStream.println(messageUtil.getMessageAboutWrongFormat());
            firstAndLastName = scanner.nextLine();
        }
        return firstAndLastName;
    }

    private static class MessageUtil {
        private final MessageSource messageSource;

        private final Locale locale;

        private MessageUtil(MessageSource messageSource, Locale locale) {
            this.messageSource = messageSource;
            if (localePresent(locale)) {
                this.locale = locale;
            } else {
                throw new IllegalArgumentException("Locale not supported");
            }
        }

        private boolean localePresent(Locale locale) {
            try {
                messageSource.getMessage("present", new Object[0], locale);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        private String getMessageAskingForFirstAndLastName() {
            return messageSource.getMessage("name", new Object[0], locale);
        }

        private String getMessageAboutWrongFormat() {
            return messageSource.getMessage("wrong-format", new Object[0], locale);
        }

        private String getMessageForRightAnswer() {
            return messageSource.getMessage("right-answer", new Object[0], locale);
        }

        private String getMessageForWrongAnswer() {
            return messageSource.getMessage("wrong-answer", new Object[0], locale);
        }

        private String getResultMessage() {
            return messageSource.getMessage("result", new Object[0], locale);
        }

        private String getMessageForPassingTest() {
            return messageSource.getMessage("test-passed", new Object[0], locale);
        }

        private String getMessageForFailingTest() {
            return messageSource.getMessage("test-failed", new Object[0], locale);
        }
    }

}