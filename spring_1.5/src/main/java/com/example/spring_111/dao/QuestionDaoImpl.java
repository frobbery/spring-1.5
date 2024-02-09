package com.example.spring_111.dao;

import com.example.spring_111.config.QuestionConfig;
import com.example.spring_111.domain.QuestionWithAnswers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.spring_111.util.QuestionWithAnswersUtil.getQuestionWithAnswersFromLine;

@Repository
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final List<QuestionWithAnswers> questions = new ArrayList<>();

    private final QuestionConfig questionConfig;

    @Override
    public void addQuestion(QuestionWithAnswers question) {
        questions.add(question);
    }

    @Override
    public List<QuestionWithAnswers> getAllQuestions() {
        return questions;
    }

    @Override
    public void addQuestionsFromFile() throws IOException, URISyntaxException {
        var uri = Objects.requireNonNull(getClass().getResource(questionConfig.getPath())).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri));
        List<Locale> supportedLocale = Arrays.stream(lines.get(0).split(",")).map(Locale::new)
                .collect(Collectors.toList());
        lines.stream().skip(1).forEach(line -> addQuestion(getQuestionWithAnswersFromLine(line, supportedLocale)));
    }
}
