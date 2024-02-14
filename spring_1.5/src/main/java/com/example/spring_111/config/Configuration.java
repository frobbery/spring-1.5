package com.example.spring_111.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(QuestionConfig.class)
@PropertySource(value = "/application.yml", factory = YamlPropertySourceFactory.class)
public class Configuration {
    
    @Bean
    MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
