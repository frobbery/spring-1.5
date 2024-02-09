package com.example.spring_111.service.input_output;

import com.example.spring_111.domain.QuestionWithAnswers;
import com.example.spring_111.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class InputOutputServiceImpl implements InputOutputService {
    private final MessageService messageService;
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream printStream = new PrintStream(System.out);
    private Locale locale = Locale.ENGLISH;

    @Override
    public void setLocale(Locale locale) {
        if (messageService.localePresent(locale)) {
            messageService.setLocale(locale);
            this.locale = locale;
        } else {
            throw new IllegalArgumentException("Locale not supported");
        }
    }

    @Override
    public void printAllQuestions(List<QuestionWithAnswers> questions, Locale locale) {
        for (QuestionWithAnswers question : questions) {
            print(question.getPrintedInLocale(locale));
        }
    }

    @Override
    public String getStudentLastAndFirstName() {
        print(messageService.getMessageAskingForFirstAndLastName());
        var firstAndLastName = scanner.nextLine();
        while (!firstAndLastName.contains(" ") && firstAndLastName.split(" ").length != 2) {
            print(messageService.getMessageAboutWrongFormat());
            firstAndLastName = scanner.nextLine();
        }
        return firstAndLastName;
    }

    @Override
    public String printQuestionAndGetAnswer(QuestionWithAnswers question) {
        printStream.println(question.getPrintedInLocale(locale));
        return scanner.nextLine();
    }

    @Override
    public void printAnswerResult(boolean result) {
        if (result) {
            print(messageService.getMessageForRightAnswer());
        } else {
            print(messageService.getMessageForWrongAnswer());
        }
    }
    @Override
    public void printResultOfStudent(String studentName) {
        print(MessageFormat.format(messageService.getResultMessage(), studentName));
    }

    @Override
    public void printTestResult(boolean passed) {
        if (passed) {
            print(messageService.getMessageForPassingTest());
        } else {
            print(messageService.getMessageForFailingTest());
        }
    }

    protected void print(String toPrint) {
        printStream.println(toPrint);
    }
}
