package com.project.lala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LalaApplication.class, args);
	}
}
