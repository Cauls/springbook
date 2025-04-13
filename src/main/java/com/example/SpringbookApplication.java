package com.example;

import com.example.entities.*;
import com.example.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration.class)
public class SpringbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbookApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
        return "index";
	}

	@Autowired
	private AutoresRepository autoresRepository;

	@Autowired
	private ClientesRepository clientesRepository;

	@Autowired
	private LibrosRepository librosRepository;

	@Bean
	CommandLineRunner innit(){
		return args -> {
			librosRepository.findAll().forEach(libro -> {
				log.info(libro.getTitulo());
			});
		};
	}
}
