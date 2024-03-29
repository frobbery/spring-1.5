package com.example.spring_111;

import com.example.spring_111.service.question.QuestionService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;
import java.util.Objects;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
		QuestionService service = context.getBean(QuestionService.class);
		service.conductTesting(new Locale(Objects.requireNonNull(context.getEnvironment().getProperty("initial-locale"))));
	}

}
