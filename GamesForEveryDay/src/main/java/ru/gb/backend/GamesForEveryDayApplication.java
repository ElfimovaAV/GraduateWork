package ru.gb.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.gb.backend.models.DayOfWeek;
import ru.gb.backend.models.ScheduleForAWeek;
import ru.gb.backend.repository.ScheduleForAWeekRepository;
import ru.gb.backend.service.ScheduleForAWeekService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
@RequiredArgsConstructor
public class GamesForEveryDayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamesForEveryDayApplication.class, args);
	}


}
