package ru.gb.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GamesForEveryDayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamesForEveryDayApplication.class, args);
	}


}
