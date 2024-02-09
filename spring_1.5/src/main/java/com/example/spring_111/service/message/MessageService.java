package com.example.spring_111.service.message;

import java.util.Locale;

public interface MessageService {

    boolean localePresent(Locale locale);

    String getMessageAskingForFirstAndLastName();

    String getMessageAboutWrongFormat();

    String getMessageForRightAnswer();

    String getMessageForWrongAnswer();

    String getResultMessage();

    String getMessageForPassingTest();

    String getMessageForFailingTest();

    void setLocale(Locale locale);
}
