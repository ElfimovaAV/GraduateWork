package ru.gb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.models.Game;
import ru.gb.backend.models.Rules;
import ru.gb.backend.models.User;
import ru.gb.backend.service.GameService;
import ru.gb.backend.service.RuleService;
import ru.gb.backend.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final GameService gameService;
    private final RuleService rulesService;
    private final UserServiceImpl userService;
    /**
     * Перехват команды на получения списка всех игр
     * @return список игр и код ответа 200
     */
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {
        return new ResponseEntity<>(gameService.getAllGames(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание игры
     * @param game
     * @return созданную игру и код ответа 201
     */
    @PostMapping("/games")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        return new ResponseEntity<>(gameService.createGame(game), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод игры по id
     * @param id
     * @return игру с заданным id и код ответа 200 либо код ответа 400 и новую пустую игру, если нет игры с заданным id
     */
    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGame(@PathVariable("id") Long id) {
        Game gameById;
        try {
            gameById = gameService.getGameById(id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Game());
        }
        return new ResponseEntity<>(gameById, HttpStatus.OK);
    }

    /**
     * Перехват команды на обновление игры с поиском нужной по id
     * @param id
     * @param game
     * @return отредактированную игру и код ответа 200
     */
    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable("id") Long id, @RequestBody Game game) {
        return new ResponseEntity<>(gameService.updateGame(id, game), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление игры по ее id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }
    /**
     * Перехват команды на получения списка всех правил игры
     * @return список правил игры и код ответа 200
     */
    @GetMapping("/rules")
    public ResponseEntity<List<Rules>> getAllRules() {
        return new ResponseEntity<>(rulesService.getAllRules(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание правил игры
     * @param rules
     * @return созданные правила игры и код ответа 201
     */
    @PostMapping("/rules")
    public ResponseEntity<Rules> createRules(@RequestBody Rules rules) {
        return new ResponseEntity<>(rulesService.createRules(rules), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод правил игры по id
     * @param id
     * @return правила игры с заданным id и код ответа 200 либо код ответа 400 и новую пустые правила игры, если нет правил игры с заданным id
     */
    @GetMapping("/rules/{id}")
    public ResponseEntity<Rules> getRules(@PathVariable("id") Long id) {
        Rules rulesById;
        try {
            rulesById = rulesService.getRulesById(id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Rules());
        }
        return new ResponseEntity<>(rulesById, HttpStatus.OK);
    }

    /**
     * Перехват команды на обновление правил игры с поиском нужной по id
     * @param id
     * @param rules
     * @return отредактированные правила игры и код ответа 200
     */
    @PutMapping("/rules/{id}")
    public ResponseEntity<Rules> updateRules(@PathVariable("id") Long id, @RequestBody Rules rules) {
        return new ResponseEntity<>(rulesService.updateRules(id, rules), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление правил игры по их id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/rules/{id}")
    public ResponseEntity<Void> deleteGameRules(@PathVariable("id") Long id){
        rulesService.deleteRules(id);
        return ResponseEntity.ok().build();
    }
    /**
     * Перехват команды на получения списка всех пользователей
     * @return список пользователей и код ответа 200
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание пользователя
     * @param user
     * @return созданного пользователя и код ответа 201
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
    /**
     * Перехват команды на вывод пользователя по id
     * @param id
     * @return пользователя с заданным id и код ответа 200 либо код ответа 400 и нового пустого пользователя, если нет пользователя с заданным id
     */
    @GetMapping("/users/{id}")
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
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление пользователя по его id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
