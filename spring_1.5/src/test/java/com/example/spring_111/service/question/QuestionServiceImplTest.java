package com.example.spring_111.service.question;

import com.example.spring_111.config.QuestionConfig;
import com.example.spring_111.dao.QuestionDao;
import com.example.spring_111.domain.QuestionWithAnswers;
import com.example.spring_111.service.input_output.InputOutputService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Класс QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionConfig questionConfig;

    @Mock
    private InputOutputService inputOutputService;

    @InjectMocks
    private QuestionServiceImpl sut;

    @Test
    @DisplayName("Должен выводить все вопросы")
    void shouldPrintAllQuestions() {
        //given
        var questions = List.of(mock(QuestionWithAnswers.class));
        when(questionDao.getAllQuestions()).thenReturn(questions);
        var locale = Locale.ENGLISH;

        //when
        sut.printAllQuestionsWithAnswers(locale);

        //then
        verify(questionDao, times(1)).getAllQuestions();
        verify(inputOutputService, times(1)).printAllQuestions(questions, locale);
    }

    @Test
    @DisplayName("Должен вовзращать true, когда тестирование пройдено")
    void shouldReturnTrue_whenTestingPassed() {
        //given
        var question = question();
        when(questionDao.getAllQuestions()).thenReturn(List.of(question));
        when(questionConfig.getNumber()).thenReturn(1);
        when(inputOutputService.printQuestionAndGetAnswer(question)).thenReturn("answer1");

        //when
        var result = sut.conductTesting(Locale.ENGLISH);

        //then
        assertTrue(result);
        verify(questionDao, times(1)).getAllQuestions();
        verify(inputOutputService, times(1)).getStudentLastAndFirstName();
        verify(inputOutputService, times(1)).printAnswerResult(true);
        verify(inputOutputService, times(1)).printTestResult(true);
    }

    @Test
    @DisplayName("Должен вовзращать false, когда тестирование не пройдено")
    void shouldReturnFalse_whenTestingNotPassed() {
        //given
        var question = question();
        when(questionDao.getAllQuestions()).thenReturn(List.of(question));
        when(questionConfig.getNumber()).thenReturn(1);
        when(inputOutputService.printQuestionAndGetAnswer(question)).thenReturn("answer2");

        //when
        var result = sut.conductTesting(Locale.ENGLISH);

        //then
        assertFalse(result);
        verify(questionDao, times(1)).getAllQuestions();
        verify(inputOutputService, times(1)).getStudentLastAndFirstName();
        verify(inputOutputService, times(1)).printAnswerResult(false);
        verify(inputOutputService, times(1)).printTestResult(false);
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