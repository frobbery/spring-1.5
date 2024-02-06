package com.example.spring_111.service;

import com.example.spring_111.config.QuestionConfig;
import com.example.spring_111.dao.QuestionDao;
import com.example.spring_111.domain.QuestionWithAnswers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Класс QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionConfig questionConfig;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private QuestionServiceImpl sut;

    @Test
    @DisplayName("Должен добавлять вопросы из файла")
    void shouldAddQuestionsFromFile() throws IOException, URISyntaxException {
        //given
        when(questionConfig.getPath()).thenReturn("/example.csv");

        //when
        sut.addQuestions();

        //then
        verify(questionDao, times(1)).addQuestion(any());
    }

    @Test
    @DisplayName("Должен выводить все вопросы")
    void shouldPrintAllQuestions() {
        //given
        var question = question();
        when(questionDao.getAllQuestions()).thenReturn(List.of(question));
        var printStream = mock(PrintStream.class);

        //when
        sut.printAllQuestionsWithAnswers(printStream, Locale.ENGLISH);

        //then
        verify(questionDao, times(1)).getAllQuestions();
    }

    @Test
    @DisplayName("Должен выводить ошибку, когда запрашивается неподдерживаемая локализация, при выводе вопросов")
    void shouldThrow_whenPrintAllQuestionsInNotSupportedLocale() {
        //given
        var question = question();
        when(questionDao.getAllQuestions()).thenReturn(List.of(question));
        var printStream = mock(PrintStream.class);

        //when
        var result = assertThrows(IllegalArgumentException.class, () -> sut.printAllQuestionsWithAnswers(printStream, Locale.CHINESE));

        //then
        assertEquals("Locale not supported", result.getMessage());
        verify(questionDao, times(1)).getAllQuestions();
        verify(messageSource, times(0)).getMessage(any(), any(), any());
    }

    @Test
    @DisplayName("Должен вовзращать true, когда тестирование пройдено")
    void shouldReturnTrue_whenTestingPassed() {
        //given
        var question = question();
        when(questionDao.getAllQuestions()).thenReturn(List.of(question));
        var printStream = mock(PrintStream.class);
        var scanner = new Scanner(new ByteArrayInputStream("First Last\nanswer1".getBytes()));
        when(questionConfig.getNumber()).thenReturn(1);
        when(messageSource.getMessage("present", new Object[0], Locale.ENGLISH)).thenReturn("");

        //when
        var result = sut.conductTesting(printStream, scanner, Locale.ENGLISH);

        //then
        assertTrue(result);
        verify(questionDao, times(1)).getAllQuestions();
        verify(messageSource, times(5)).getMessage(any(), any(), any());
    }

    @Test
    @DisplayName("Должен вовзращать false, когда тестирование не пройдено")
    void shouldReturnFalse_whenTestingNotPassed() {
        //given
        var question = question();
        when(questionDao.getAllQuestions()).thenReturn(List.of(question));
        var printStream = mock(PrintStream.class);
        var scanner = new Scanner(new ByteArrayInputStream("First Last\nanswer2".getBytes()));
        when(questionConfig.getNumber()).thenReturn(1);
        when(messageSource.getMessage("present", new Object[0], Locale.ENGLISH)).thenReturn("");

        //when
        var result = sut.conductTesting(printStream, scanner, Locale.ENGLISH);

        //then
        assertFalse(result);
        verify(questionDao, times(1)).getAllQuestions();
        verify(messageSource, times(5)).getMessage(any(), any(), any());
    }

    @Test
    @DisplayName("Должен выбрасывать ошибку, когда тестирование запрашивается в неподдерживаемой локализации")
    void shouldThrow_whenTestingInWrongLocale() {
        //given
        var printStream = mock(PrintStream.class);
        var scanner = new Scanner(new ByteArrayInputStream("".getBytes()));
        when(messageSource.getMessage("present", new Object[0], Locale.ENGLISH)).thenThrow(new RuntimeException());

        //when
        var result = assertThrows(IllegalArgumentException.class, () -> sut.conductTesting(printStream, scanner, Locale.ENGLISH));

        //then
        assertEquals("Locale not supported", result.getMessage());
        verify(messageSource, times(1)).getMessage(any(), any(), any());
    }

    private QuestionWithAnswers question() {
        var enLocale = new Locale("en");
        var ruLocale = new Locale("ru_Ru");
        return new QuestionWithAnswers(Map.of(enLocale, "question", ruLocale, "вопрос"),
                Map.of(enLocale, "answer1", ruLocale, "ответ1"),
                Arrays.asList(Map.of(enLocale, "answer1", ruLocale, "ответ1"),
                        Map.of(enLocale, "answer2", ruLocale, "ответ2")));
    }
}