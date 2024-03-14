package ru.gb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.models.Game;
import ru.gb.backend.models.ScheduleForAWeek;
import ru.gb.backend.models.User;
import ru.gb.backend.repository.ScheduleForAWeekRepository;
import ru.gb.backend.service.ScheduleForAWeekService;
import ru.gb.backend.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ScheduleForAWeekService scheduleForAWeekService;
    /**
     * Перехват команды на получения списка всех пользователей
     * @return список пользователей и код ответа 200
     */
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание пользователя
     * @param user
     * @return созданного пользователя и код ответа 201
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
    /**
     * Перехват команды на вывод пользователя по id
     * @param id
     * @return пользователя с заданным id и код ответа 200 либо код ответа 400 и нового пустого пользователя, если нет пользователя с заданным id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User userById;
        try {
            userById = userService.getUserById(id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    /**
     * Перехват команды на обновление пользователя с поиском нужного по id
     * @param id
     * @param user
     * @return отредактированного пользователя и код ответа 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление пользователя по его id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search_by_child_sex")
    public ResponseEntity<List<Game>> getGamesByChildSex(@RequestParam Long id) {
        return new ResponseEntity<>(userService.getGamesByChildSex(id), HttpStatus.OK);
    }

    @GetMapping("/search_by_child_age")
    public ResponseEntity<List<Game>> getGamesByChildAge(@RequestParam Long id) {
        return new ResponseEntity<>(userService.getGamesByChildAge(id), HttpStatus.OK);
    }

    @GetMapping("/search_by_child_sex_and_age")
    public ResponseEntity<List<Game>> getGamesByChildSexAndAge(@RequestParam Long id) {
        return new ResponseEntity<>(userService.getGamesByChildSexAndAge(id), HttpStatus.OK);
    }
    @PostMapping("/schedule_for_a_week")
    public ResponseEntity<List<ScheduleForAWeek>> createScheduleForAWeek(@RequestParam Long id){
        return new ResponseEntity<>(userService.createScheduleForAWeek(id), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод недельного расписания пользователя по id
     * @param id
     * @return недельное расписание пользователя с заданным id и код ответа 200 либо код ответа 400 и новое пустое расписание пользователя
     */
    @GetMapping("/schedule_for_a_week/{id}")
    public ResponseEntity<List<ScheduleForAWeek>> getScheduleForAWeekByUser(@PathVariable("id") Long id) {
        String email = Objects.requireNonNull(getUser(id).getBody()).getEmail();
        List<ScheduleForAWeek> scheduleForAWeekByEmail;
        try {
            scheduleForAWeekByEmail = scheduleForAWeekService.getScheduleForAWeekByEmail(email);
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
        return new ResponseEntity<>(scheduleForAWeekByEmail, HttpStatus.OK);
    }

}
