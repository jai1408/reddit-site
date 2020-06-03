package com.citi.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringRedditClone {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedditClone.class, args);
	}

}
