package com.example.spring_111.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс QuestionWithAnswersUtil")
class QuestionWithAnswersUtilTest {

    @DisplayName("Должен формировать вопрос с ответами из csv-строки")
    @Test
    void shouldReturnQuestion_whenGetQuestionFromLine() {
        //given
        var line = "1,someQuestion,вопрос,someAnswer,ответ";
        var enLocale = new Locale("en");
        var ruLocale = new Locale("ru_Ru");
        var supportedLocale = List.of(enLocale, ruLocale);

        //when
        var result = QuestionWithAnswersUtil.getQuestionWithAnswersFromLine(line, supportedLocale);

        //then
        assertThat(result).hasFieldOrPropertyWithValue("questionWithLocale", Map.of(enLocale, "someQuestion",
                        ruLocale, "вопрос"))
                .hasFieldOrPropertyWithValue("rightAnswerWithLocale", Map.of(enLocale, "someAnswer",
                        ruLocale, "ответ"))
                .hasFieldOrPropertyWithValue("answersWithLocale", List.of(Map.of(enLocale, "someAnswer",
                        ruLocale, "ответ")));
    }

    @DisplayName("Должен выбрасывать ошибку, если строка невереного формата")
    @ParameterizedTest
    @MethodSource("wrongLines")
    void shouldThrow_whenGetQuestionFromLineOfWrongFormat(String line) {
        //when
        var result = assertThrows(IllegalArgumentException.class,
                () -> QuestionWithAnswersUtil.getQuestionWithAnswersFromLine(line, List.of(Locale.ENGLISH)));

        //then
        assertEquals("Line is in wrong format", result.getMessage());
    }

    private static Stream<Arguments> wrongLines() {
        return Stream.of(
                Arguments.of("noAnswerNum"),
                Arguments.of("3,invalidAnswerNum,invalidAnswerNum")
        );
    }
}