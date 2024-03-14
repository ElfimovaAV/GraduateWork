package ru.gb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.models.Game;
import ru.gb.backend.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    /**
     * Перехват команды на получения списка всех игр
     * @return список игр и код ответа 200
     */
    @GetMapping
    public ResponseEntity<List<Game>> getAll() {
        return new ResponseEntity<>(gameService.getAllGames(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание игры
     * @param game
     * @return созданную игру и код ответа 201
     */
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        return new ResponseEntity<>(gameService.createGame(game), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод игры по id
     * @param id
     * @return игру с заданным id и код ответа 200 либо код ответа 400 и новую пустую игру, если нет игры с заданным id
     */
    @GetMapping("/{id}")
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
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable("id") Long id, @RequestBody Game game) {
        return new ResponseEntity<>(gameService.updateGame(id, game), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление игры по ее id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }

}
