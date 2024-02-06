package com.example.spring_111.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "questions")
public class QuestionConfig {

    private String path;

    private Integer number;

    public void setPath(String path) {
        this.path = path;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
