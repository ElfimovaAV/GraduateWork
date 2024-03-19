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
import ru.gb.backend.service.ScheduleForAWeekService;
import ru.gb.backend.service.impl.JwtServiceImpl;
import ru.gb.backend.service.impl.UserServiceImpl;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final ScheduleForAWeekService scheduleForAWeekService;
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
        return userService.findByUsername(username);
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
        return new ResponseEntity<>(userService.getGamesByChildSex(getUserByToken(request).getId()), HttpStatus.OK);
    }

    /**
     * Перехват команды на получение списка подходящих по возрасту ребенка пользователя игр
     * @param request
     * @return список подходящих игр и код ответа 200
     */
    @GetMapping("/search_by_child_age")
    public ResponseEntity<List<Game>> getGamesByChildAge(HttpServletRequest request) {
        return new ResponseEntity<>(userService.getGamesByChildAge(getUserByToken(request).getId()), HttpStatus.OK);
    }

    /**
     * Перехват команды на получение списка подходящих по возрасту и полу ребенка пользователя игр
     * @param request
     * @return список подходящих игр и код ответа 200
     */
    @GetMapping("/search_by_child_sex_and_age")
    public ResponseEntity<List<Game>> getGamesByChildSexAndAge(HttpServletRequest request) {
        return new ResponseEntity<>(userService.getGamesByChildSexAndAge(getUserByToken(request).getId()), HttpStatus.OK);
    }

    /**
     * Перезват команды на создание недельного расписания на основе подходящих по полу и возрасту ребенка пользователя игр
     * @param request
     * @return недельное расписание и код ответа 201
     */
    @PostMapping("/schedule_for_a_week")
    public ResponseEntity<List<ScheduleForAWeek>> createScheduleForAWeek(HttpServletRequest request){
        return new ResponseEntity<>(userService.createScheduleForAWeek(getUserByToken(request).getId()), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод недельного расписания пользователя по id
     * @param request
     * @return недельное расписание пользователя с заданным id и код ответа 200 либо код ответа 400 и новое пустое расписание пользователя
     */
    @GetMapping("/schedule_for_a_week/get")
    public ResponseEntity<List<ScheduleForAWeek>> getScheduleForAWeekByUserId(HttpServletRequest request) {
        User user = getUserByToken(request);
        List<ScheduleForAWeek> scheduleForAWeekByUserId;
        try {
            scheduleForAWeekByUserId = scheduleForAWeekService.getScheduleForAWeekByUserId(user.getId());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new List<ScheduleForAWeek>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<ScheduleForAWeek> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] a) {
                    return null;
                }

                @Override
                public boolean add(ScheduleForAWeek scheduleForAWeek) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends ScheduleForAWeek> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, Collection<? extends ScheduleForAWeek> c) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public ScheduleForAWeek get(int index) {
                    return null;
                }

                @Override
                public ScheduleForAWeek set(int index, ScheduleForAWeek element) {
                    return null;
                }

                @Override
                public void add(int index, ScheduleForAWeek element) {

                }

                @Override
                public ScheduleForAWeek remove(int index) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @Override
                public ListIterator<ScheduleForAWeek> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<ScheduleForAWeek> listIterator(int index) {
                    return null;
                }

                @Override
                public List<ScheduleForAWeek> subList(int fromIndex, int toIndex) {
                    return null;
                }
            });
        }
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
        return new ResponseEntity<>(userService.getGameForToday(dayOfWeek, getUserByToken(request).getId()).orElse(null), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление игры по ее id
     * @param request
     * @return код ответа 200
     */
    @DeleteMapping("/schedule_for_a_week")
    public ResponseEntity<Void> deleteScheduleForAWeekByUserId(HttpServletRequest request){
        scheduleForAWeekService.deleteScheduleForAWeekByUserId(getUserByToken(request).getId());
        return ResponseEntity.ok().build();
    }

}
