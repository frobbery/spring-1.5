package com.example.spring_111.service.input_output;

import com.example.spring_111.domain.QuestionWithAnswers;

import java.util.List;
import java.util.Locale;

public interface InputOutputService {

    void printAllQuestions(List<QuestionWithAnswers> questions, Locale locale);

    String getStudentLastAndFirstName();

    String printQuestionAndGetAnswer(QuestionWithAnswers question);

    void printAnswerResult(boolean result);

    void printResultOfStudent(String studentName);

    void printTestResult(boolean passed);

    void setLocale(Locale locale);
}
