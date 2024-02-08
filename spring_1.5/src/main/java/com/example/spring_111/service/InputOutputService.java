package com.example.spring_111.service;

import com.example.spring_111.domain.QuestionWithAnswers;

import java.util.List;

public interface InputOutputService {

    void printAllQuestions(List<QuestionWithAnswers> questions);

    String getStudentLastAndFirstName();

    String printQuestionAndGetAnswer(QuestionWithAnswers question);

    void printAnswerResult(boolean result);

    void printResultOfStudent(String studentName);

    void printTestResult(boolean passed);
}
