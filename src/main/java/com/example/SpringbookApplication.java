package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class SpringbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbookApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}
}
