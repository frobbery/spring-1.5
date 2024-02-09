package com.example.spring_111.service.input_output;

import com.example.spring_111.domain.QuestionWithAnswers;
import com.example.spring_111.service.message.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputOutputServiceImplTest {

    @Mock
    private MessageService messageService;

    private InputOutputServiceImpl sut;

    @BeforeEach
    void setSut() {
        sut = Mockito.spy(new InputOutputServiceImpl(messageService));
    }

    @Test
    @DisplayName("Должен задавать локализацию")
    void shouldSetGivenLocale() {
        //given
        var locale = Locale.ENGLISH;
        when(messageService.localePresent(locale)).thenReturn(true);

        //when
        sut.setLocale(locale);

        //then
        verify(messageService, times(1)).setLocale(locale);
    }

    @Test
    @DisplayName("Должен бросать ошибку когда переданная локализация не поддерживается")
    void shouldThrowWhenGivenLocaleNotSupported() {
        //given
        var locale = Locale.ENGLISH;
        when(messageService.localePresent(locale)).thenReturn(false);

        //when
        var result = assertThrows(IllegalArgumentException.class, () -> sut.setLocale(locale));

        //then
        assertEquals("Locale not supported", result.getMessage());
    }


    @Test
    @DisplayName("Должен печатать вопросы из списка")
    void shouldPrintQuestionsFromList() {
        //given
        var question = Mockito.mock(QuestionWithAnswers.class);
        var questions = List.of(question);
        var locale = Locale.ENGLISH;

        //when
        sut.printAllQuestions(questions, locale);

        //then
        verify(sut, times(1)).print(question.getPrintedInLocale(locale));
    }

    @Test
    @DisplayName("Должен возвращать введенные имя и фамилию")
    void shouldReturnPrintedNameAndSurname() {
        //given
        var name = "first last";
        var inputStream = new ByteArrayInputStream(name.getBytes());
        System.setIn(inputStream);
        var sut = Mockito.spy(new InputOutputServiceImpl(messageService));
        when(messageService.getMessageAskingForFirstAndLastName()).thenReturn("messageAskingForFirstAndLastName");

        //when
        var result = sut.getStudentLastAndFirstName();

        //then
        verify(sut, times(1)).print("messageAskingForFirstAndLastName");
        assertEquals(name, result);
    }

    @Test
    @DisplayName("Должен заново просить ввести имя и фамилию, когда введено в неверном формате")
    void shouldAskAgainWhenInputNameOfWrongFormat() {
        //given
        var inputStream = new ByteArrayInputStream("wrongName\nfirst last".getBytes());
        System.setIn(inputStream);
        var sut = Mockito.spy(new InputOutputServiceImpl(messageService));
        when(messageService.getMessageAskingForFirstAndLastName()).thenReturn("messageAskingForFirstAndLastName");
        when(messageService.getMessageAboutWrongFormat()).thenReturn("messageAboutWrongFormat");

        //when
        var result = sut.getStudentLastAndFirstName();

        //then
        verify(sut, times(1)).print("messageAboutWrongFormat");
        verify(sut, times(1)).print("messageAskingForFirstAndLastName");
        assertEquals("first last", result);
    }

    @Test
    @DisplayName("Должен выводить строку с результатом студента")
    void shouldPrintResultOfAStudent() {
        //given
        when(messageService.getResultMessage()).thenReturn("resultMessage");

        //when
        sut.printResultOfStudent("studentName");

        //then
        verify(sut, times(1)).print("resultMessage");
    }

    @Test
    @DisplayName("Должен выводить строку с подтверждением правильного ответа")
    void shouldPrintAnswerRight() {
        //given
        when(messageService.getMessageForRightAnswer()).thenReturn("messageForRightAnswer");

        //when
        sut.printAnswerResult(true);

        //then
        verify(sut, times(1)).print("messageForRightAnswer");
    }

    @Test
    @DisplayName("Должен выводить строку с подтверждением неправильного ответа")
    void shouldPrintAnswerWrong() {
        //given
        when(messageService.getMessageForWrongAnswer()).thenReturn("messageForWrongAnswer");

        //when
        sut.printAnswerResult(false);

        //then
        verify(sut, times(1)).print("messageForWrongAnswer");
    }

    @Test
    @DisplayName("Должен выводить строку о прохождении теста")
    void shouldPrintTestResultPassed() {
        //given
        when(messageService.getMessageForPassingTest()).thenReturn("messageForPassingTest");

        //when
        sut.printTestResult(true);

        //then
        verify(sut, times(1)).print("messageForPassingTest");
    }

    @Test
    @DisplayName("Должен выводить строку о непрохождении теста")
    void shouldPrintTestResultFailed() {
        //given
        when(messageService.getMessageForFailingTest()).thenReturn("messageForFailingTest");

        //when
        sut.printTestResult(false);

        //then
        verify(sut, times(1)).print("messageForFailingTest");
    }
}