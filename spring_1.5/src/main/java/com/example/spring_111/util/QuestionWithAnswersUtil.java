package com.example.spring_111.util;

import com.example.spring_111.domain.QuestionWithAnswers;

import java.util.*;

public final class QuestionWithAnswersUtil {

    public static QuestionWithAnswers getQuestionWithAnswersFromLine(String line, List<Locale> supportedLocales) {
        try {
            String[] separatedStrings = line.split(",");
            var answerNum = Integer.parseInt(separatedStrings[0]);
            var questions = new HashMap<Locale, String>();
            int index = 1;
            for (Locale supportedLocale : supportedLocales) {
                questions.put(supportedLocale, separatedStrings[index]);
                index++;
            }
            var rightAnswers = new HashMap<Locale, String>();
            var answers = new ArrayList<Map<Locale, String>>();
            for (Locale supportedLocale : supportedLocales) {
                rightAnswers.put(supportedLocale, separatedStrings[index]);
                index++;
            }
            answers.add(rightAnswers);
            for (int i = 0; i < answerNum - 1; i++) {
                var answerMap = new HashMap<Locale, String>();
                for (Locale supportedLocale : supportedLocales) {
                    answerMap.put(supportedLocale, separatedStrings[index]);
                    index++;
                }
                answers.add(answerMap);
            }
            return new QuestionWithAnswers(questions, rightAnswers, answers);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Line is in wrong format");
        }
    }
}
