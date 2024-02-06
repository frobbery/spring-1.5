package com.example.spring_111.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс QuestionWithAnswers")
class QuestionWithAnswersTest {

    private final static Locale EN_LOCALE = new Locale("en");
    private final static Locale RU_LOCALE = new Locale("ru_RU");

    @DisplayName("Должен правильно формировать строку из вопроса в запрашиваемой локализации")
    @ParameterizedTest
    @MethodSource("getLocalesAndLocalisedQuestions")
    void shouldReturnQuestionStringInRightLocale_whenToStringCalled(Locale locale, String expectedString) {
        //given
        var question = question();

        //when
        var printedQuestion = question.getPrintedInLocale(locale);

        //then
        assertThat(printedQuestion)
                .matches(expectedString);
    }

    @DisplayName("Должен выбрасывать ошибку при формировании строки из вопроса, если локализация не поддерживается")
    @Test
    void shouldThrow_whenPrintInLocaleNotSupported() {
        //given
        var question = question();

        //when
        var result = assertThrows(IllegalArgumentException.class, () -> question.getPrintedInLocale(Locale.CHINA));

        //then
        assertEquals("Locale not supported", result.getMessage());
    }

    @DisplayName("Должен подтверждать, что ответ правильный в требуемой локализации")
    @ParameterizedTest
    @MethodSource("getAnswersWithLocales")
    void shouldCheckIfAnswerRightInLocalisation(String answer, Locale locale, Boolean expectedResult) {
        //given
        var question = question();

        //when
        var result = question.answerIsRight(answer, locale);

        //then
        assertEquals(expectedResult, result);
    }

    private QuestionWithAnswers question() {
        return new QuestionWithAnswers(Map.of(EN_LOCALE, "question", RU_LOCALE, "вопрос"),
                Map.of(EN_LOCALE, "answer1", RU_LOCALE, "ответ1"),
                Arrays.asList(Map.of(EN_LOCALE, "answer1", RU_LOCALE, "ответ1"),
                        Map.of(EN_LOCALE, "answer2", RU_LOCALE, "ответ2")));
    }

    private static Stream<Arguments> getLocalesAndLocalisedQuestions() {
        return Stream.of(
                Arguments.of(EN_LOCALE, "question\\?\n1\\. (answer1|answer2)\n2\\. (answer1|answer2)\n"),
                Arguments.of(RU_LOCALE, "вопрос\\?\n1\\. (ответ1|ответ2)\n2\\. (ответ1|ответ2)\n")
        );
    }

    private static Stream<Arguments> getAnswersWithLocales() {
        return Stream.of(
                Arguments.of("answer1", EN_LOCALE, true),
                Arguments.of("answer2", EN_LOCALE, false),
                Arguments.of("ответ1", EN_LOCALE, false),
                Arguments.of("ответ1", RU_LOCALE, true),
                Arguments.of("ответ2", RU_LOCALE, false),
                Arguments.of("answer1", RU_LOCALE, false)
        );
    }
}