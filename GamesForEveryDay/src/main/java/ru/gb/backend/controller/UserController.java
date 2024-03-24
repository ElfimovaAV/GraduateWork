package ru.gb.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.models.DayOfWeek;
import ru.gb.backend.models.Game;
import ru.gb.backend.models.ScheduleForAWeek;
import ru.gb.backend.models.User;
import ru.gb.backend.services.GameService;
import ru.gb.backend.services.ScheduleForAWeekService;
import ru.gb.backend.services.impl.JwtServiceImpl;
import ru.gb.backend.services.UserService;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ScheduleForAWeekService scheduleForAWeekService;
    private final GameService gameService;
    private final JwtServiceImpl jwtService;

    /**
     * Вспомогательный метод для получения пользователя по его токену из хедера
     * @param request
     * @return идентифицированного пользователя
     */
    public User getUserByToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        return userService.getUserByUsername(username);
    }
    /**
     * Перехват команды на обновление пользователя с поиском нужного по id
     * @param request
     * @param user
     * @return отредактированного пользователя и код ответа 200
     */
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(HttpServletRequest request, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(getUserByToken(request).getId(), user), HttpStatus.OK);
    }

    /**
     * Перехват команды на получение списка подходящих по полу ребенка пользователя игр
     * @param request
     * @return список подходящих игр и код ответа 200
     */
    @GetMapping("/search_by_child_sex")
    public ResponseEntity<List<Game>> getGamesByChildSex(HttpServletRequest request) {
        return new ResponseEntity<>(gameService.getGamesByChildSex(getUserByToken(request).getId()), HttpStatus.OK);
    }

    /**
     * Перехват команды на получение списка подходящих по возрасту ребенка пользователя игр
     * @param request
     * @return список подходящих игр и код ответа 200
     */
    @GetMapping("/search_by_child_age")
    public ResponseEntity<List<Game>> getGamesByChildAge(HttpServletRequest request) {
        return new ResponseEntity<>(gameService.getGamesByChildAge(getUserByToken(request).getId()), HttpStatus.OK);
    }

    /**
     * Перехват команды на получение списка подходящих по возрасту и полу ребенка пользователя игр
     * @param request
     * @return список подходящих игр и код ответа 200
     */
    @GetMapping("/search_by_child_sex_and_age")
    public ResponseEntity<List<Game>> getGamesByChildSexAndAge(HttpServletRequest request) {
        return new ResponseEntity<>(gameService.getGamesByChildSexAndAge(getUserByToken(request).getId()), HttpStatus.OK);
    }

    /**
     * Перезват команды на создание недельного расписания на основе подходящих по полу и возрасту ребенка пользователя игр
     * @param request
     * @return недельное расписание и код ответа 201
     */
    @PostMapping("/schedule_for_a_week")
    public ResponseEntity<List<ScheduleForAWeek>> createScheduleForAWeek(HttpServletRequest request){
        return new ResponseEntity<>(gameService.createScheduleForAWeek(getUserByToken(request).getId()), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод недельного расписания аутентифицированного пользователя
     * @param request
     * @return недельное расписание аутентифицированного пользователя и код ответа 200
     */
    @GetMapping("/schedule_for_a_week/get")
    public ResponseEntity<List<ScheduleForAWeek>> getScheduleForAWeekByUserId(HttpServletRequest request) {
        User user = getUserByToken(request);
        List<ScheduleForAWeek> scheduleForAWeekByUserId = scheduleForAWeekService.getScheduleForAWeekByUserId(user.getId());
        return new ResponseEntity<>(scheduleForAWeekByUserId, HttpStatus.OK);
    }

    /**
     * Перехват команды на получение игры на нужный день недели из недельного расписания пользователя
     * @param request
     * @param dayOfWeek
     * @return game и код ответа 200
     */
    @GetMapping("/get_game_for_today")
    public ResponseEntity<Game> getGameForToday(HttpServletRequest request, @RequestParam DayOfWeek dayOfWeek) {
        return new ResponseEntity<>(scheduleForAWeekService.getGameForToday(dayOfWeek, getUserByToken(request).getId()).orElse(null), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление недельного расписания аутентифицированного пользователя
     * @param request
     * @return код ответа 200
     */
    @DeleteMapping("/schedule_for_a_week")
    public ResponseEntity<Void> deleteScheduleForAWeekByUserId(HttpServletRequest request){
        scheduleForAWeekService.deleteScheduleForAWeekByUserId(getUserByToken(request).getId());
        return ResponseEntity.ok().build();
    }

}
