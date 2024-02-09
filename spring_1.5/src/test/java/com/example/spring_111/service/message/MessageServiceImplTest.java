package com.example.spring_111.service.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DisplayName("Класс MessageServiceImpl")
@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageServiceImpl sut;

    @Test
    @DisplayName("Должен возвращать true, когда локализация присутствует")
    void shouldReturnTrue_whenLocalePresent() {
        //given
        var locale = locale();
        when(messageSource.getMessage("present", new Object[0], locale)).thenReturn("");

        //when
        var result = sut.localePresent(locale);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Должен возвращать false, когда локализация присутствует")
    void shouldReturnFalse_whenLocaleNotPresent() {
        //given
        var locale = locale();
        when(messageSource.getMessage("present", new Object[0], locale)).thenThrow(new RuntimeException());

        //when
        var result = sut.localePresent(locale);

        //then
        assertFalse(result);
    }

    @Test
    void getMessageAskingForFirstAndLastName() {
        //given
        var message = "askingForFirstAndLastName";
        when(messageSource.getMessage("name", new Object[0], Locale.ENGLISH)).thenReturn(message);

        //when
        var result = sut.getMessageAskingForFirstAndLastName();

        //then
        assertEquals(message, result);
    }

    @Test
    void getMessageAboutWrongFormat() {
        //given
        var message = "wrongFormat";
        when(messageSource.getMessage("wrong-format", new Object[0], Locale.ENGLISH)).thenReturn(message);

        //when
        var result = sut.getMessageAboutWrongFormat();

        //then
        assertEquals(message, result);
    }

    @Test
    void getMessageForRightAnswer() {
        //given
        var message = "rightAnswer";
        when(messageSource.getMessage("right-answer", new Object[0], Locale.ENGLISH)).thenReturn(message);

        //when
        var result = sut.getMessageForRightAnswer();

        //then
        assertEquals(message, result);
    }

    @Test
    void getMessageForWrongAnswer() {
        //given
        var message = "wrongAnswer";
        when(messageSource.getMessage("wrong-answer", new Object[0], Locale.ENGLISH)).thenReturn(message);

        //when
        var result = sut.getMessageForWrongAnswer();

        //then
        assertEquals(message, result);
    }

    @Test
    void getResultMessage() {
        //given
        var message = "resultMessage";
        when(messageSource.getMessage("result", new Object[0], Locale.ENGLISH)).thenReturn(message);

        //when
        var result = sut.getResultMessage();

        //then
        assertEquals(message, result);
    }

    @Test
    void getMessageForPassingTest() {
        //given
        var message = "passingTest";
        when(messageSource.getMessage("test-passed", new Object[0], Locale.ENGLISH)).thenReturn(message);

        //when
        var result = sut.getMessageForPassingTest();

        //then
        assertEquals(message, result);
    }

    @Test
    void getMessageForFailingTest() {
        //given
        var message = "failingTest";
        when(messageSource.getMessage("test-failed", new Object[0], Locale.ENGLISH)).thenReturn(message);

        //when
        var result = sut.getMessageForFailingTest();

        //then
        assertEquals(message, result);
    }

    private static Locale locale() {
        return Locale.ENGLISH;
    }
}