package ru.gb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.models.GameRules;
import ru.gb.backend.service.GameRulesService;

import java.util.List;

@RestController
@RequestMapping("/game_rules")
@RequiredArgsConstructor
public class GameRulesController {
    private final GameRulesService gameRulesService;
    /**
     * Перехват команды на получения списка всех правил игры
     * @return список правил игры и код ответа 200
     */
    @GetMapping
    public ResponseEntity<List<GameRules>> getAll() {
        return new ResponseEntity<>(gameRulesService.getAllGameRules(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание правил игры
     * @param gameRules
     * @return созданные правила игры и код ответа 201
     */
    @PostMapping
    public ResponseEntity<GameRules> createGameRules(@RequestBody GameRules gameRules) {
        return new ResponseEntity<>(gameRulesService.createGameRules(gameRules), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод правил игры по id
     * @param id
     * @return правила игры с заданным id и код ответа 200 либо код ответа 400 и новую пустые правила игры, если нет правил игры с заданным id
     */
    @GetMapping("/{id}")
    public ResponseEntity<GameRules> getGameRules(@PathVariable("id") Long id) {
        GameRules gameRulesById;
        try {
            gameRulesById = gameRulesService.getGameRulesById(id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GameRules());
        }
        return new ResponseEntity<>(gameRulesById, HttpStatus.OK);
    }

    /**
     * Перехват команды на обновление правил игры с поиском нужной по id
     * @param id
     * @param gameRules
     * @return отредактированные правила игры и код ответа 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<GameRules> updateGameRules(@PathVariable("id") Long id, @RequestBody GameRules gameRules) {
        return new ResponseEntity<>(gameRulesService.updateGameRules(id, gameRules), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление правил игры по их id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameRules(@PathVariable("id") Long id){
        gameRulesService.deleteGameRules(id);
        return ResponseEntity.ok().build();
    }
}
