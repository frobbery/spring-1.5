package com.example.spring_111.dao;

import com.example.spring_111.config.QuestionConfig;
import com.example.spring_111.domain.QuestionWithAnswers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Класс QuestionDaoImpl")
@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {

    @Mock
    private QuestionConfig questionConfig;

    @InjectMocks
    private QuestionDaoImpl sut;

    @Test
    @DisplayName("Должен добавлять вопросы")
    void shouldAddQuestion() {
        //given
        var question = mock(QuestionWithAnswers.class);

        //when
        sut.addQuestion(question);

        //then
        assertEquals(sut.getAllQuestions().size(), 1);
    }

    @Test
    @DisplayName("Должен выдавать все вопросы")
    void shouldGetAllQuestions() {
        //given
        var question = mock(QuestionWithAnswers.class);
        sut.addQuestion(question);

        //when
        var result = sut.getAllQuestions();

        //then
        Assertions.assertThat(result)
                .contains(question)
                .hasSize(1);
    }

    @Test
    @DisplayName("Должен добавлять вопросы из файла")
    void shouldAddQuestionsFromFile() throws IOException, URISyntaxException {
        //given
        when(questionConfig.getPath()).thenReturn("/example.csv");

        //when
        sut.addQuestionsFromFile();

        //then
        assertEquals(sut.getAllQuestions().size(), 2);
    }
}