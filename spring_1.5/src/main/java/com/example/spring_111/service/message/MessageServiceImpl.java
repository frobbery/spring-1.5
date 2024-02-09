package com.example.spring_111.service.message;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageSource messageSource;

    @Setter
    private Locale locale = Locale.ENGLISH;

    @Override
    public boolean localePresent(Locale locale) {
        try {
            messageSource.getMessage("present", new Object[0], locale);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getMessageAskingForFirstAndLastName() {
        return messageSource.getMessage("name", new Object[0], locale);
    }

    @Override
    public String getMessageAboutWrongFormat() {
        return messageSource.getMessage("wrong-format", new Object[0], locale);
    }

    @Override
    public String getMessageForRightAnswer() {
        return messageSource.getMessage("right-answer", new Object[0], locale);
    }

    @Override
    public String getMessageForWrongAnswer() {
        return messageSource.getMessage("wrong-answer", new Object[0], locale);
    }

    @Override
    public String getResultMessage() {
        return messageSource.getMessage("result", new Object[0], locale);
    }

    @Override
    public String getMessageForPassingTest() {
        return messageSource.getMessage("test-passed", new Object[0], locale);
    }

    @Override
    public String getMessageForFailingTest() {
        return messageSource.getMessage("test-failed", new Object[0], locale);
    }
}
