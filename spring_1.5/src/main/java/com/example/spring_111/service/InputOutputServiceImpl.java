package com.example.spring_111.service;

import com.example.spring_111.domain.QuestionWithAnswers;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class InputOutputServiceImpl implements InputOutputService {
    private MessageUtil messageUtil;
    private final Scanner scanner = new Scanner(System.in);

    private final PrintStream printStream = new PrintStream(System.out);

    //private Locale

    public InputOutputServiceImpl(MessageSource messageSource) {
        //this.messageUtil = new MessageUtil(messageSource);
    }

    @Override
    public void printAllQuestions(List<QuestionWithAnswers> questions) {
        for (QuestionWithAnswers question : questions) {
            print(question.toString());
        }
    }

    @Override
    public String getStudentLastAndFirstName() {
        print("Print your first and lastname");
        var firstAndLastName = scanner.nextLine();
        while (!firstAndLastName.contains(" ") && firstAndLastName.split(" ").length != 2) {
            print("Wrong format. Print your first and lastname");
            firstAndLastName = scanner.nextLine();
        }
        return firstAndLastName;
    }

    @Override
    public String printQuestionAndGetAnswer(QuestionWithAnswers question) {
        printStream.println(question);
        return scanner.nextLine();
    }

    @Override
    public void printAnswerResult(boolean result) {
        if (result) {
            //print(RIGHT_ANSWER);
        } else {
            //print(WRONG_ANSWER);
        }
    }
    @Override
    public void printResultOfStudent(String studentName) {
        //print(MessageFormat.format(RESULT, studentName));
    }

    @Override
    public void printTestResult(boolean passed) {
        if (passed) {
            //print(TEST_PASSED);
        } else {
            //print(TEST_FAILED);
        }
    }

    protected void print(String toPrint) {
        printStream.println(toPrint);
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
