package com.example.spring_111.domain;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class QuestionWithAnswers {
    private final Map<Locale,String> questionWithLocale;

    private final Map<Locale,String> rightAnswerWithLocale;

    private final List<Map<Locale,String>> answersWithLocale;

    @Override
    public String toString() {
        return getPrintedInLocale(Locale.getDefault());
    }

    public String getPrintedInLocale(Locale locale) {
        if (!this.questionWithLocale.containsKey(locale)) {
            throw new IllegalArgumentException("Locale not supported");
        }
        StringBuilder printedQuestion = new StringBuilder();
        printedQuestion.append(this.questionWithLocale.get(locale)).append("?\n");
        shuffleAnswers();
        for (int i = 0; i < answersWithLocale.size(); i++) {
            printedQuestion.append((i + 1)).append(". ").append(answersWithLocale.get(i).get(locale))
                    .append("\n");
        }
        return printedQuestion.toString();
    }

    public boolean answerIsRight(String answer, Locale locale) {
        return rightAnswerWithLocale.get(locale).equals(answer);
    }

    private void shuffleAnswers() {
        var random = new Random();
        answersWithLocale.sort(Comparator.comparingInt(s -> random.nextInt(3) - 1));
    }
}
