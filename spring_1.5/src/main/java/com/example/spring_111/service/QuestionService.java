package com.example.spring_111.service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Scanner;

public interface QuestionService {

    void addQuestions() throws IOException, URISyntaxException;

    void printAllQuestionsWithAnswers(PrintStream printStream, Locale locale);

    Boolean conductTesting(PrintStream printStream, Scanner scanner, Locale locale);
}
