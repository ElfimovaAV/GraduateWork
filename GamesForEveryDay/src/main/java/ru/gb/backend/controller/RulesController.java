package ru.gb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.backend.models.Rules;
import ru.gb.backend.service.RuleService;

import java.util.List;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RulesController {
    private final RuleService rulesService;
    /**
     * Перехват команды на получения списка всех правил игры
     * @return список правил игры и код ответа 200
     */
    @GetMapping
    public ResponseEntity<List<Rules>> getAll() {
        return new ResponseEntity<>(rulesService.getAllRules(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание правил игры
     * @param rules
     * @return созданные правила игры и код ответа 201
     */
    @PostMapping
    public ResponseEntity<Rules> createRules(@RequestBody Rules rules) {
        return new ResponseEntity<>(rulesService.createRules(rules), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод правил игры по id
     * @param id
     * @return правила игры с заданным id и код ответа 200 либо код ответа 400 и новую пустые правила игры, если нет правил игры с заданным id
     */
    @GetMapping("/{id}")
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
    @PutMapping("/{id}")
    public ResponseEntity<Rules> updateRules(@PathVariable("id") Long id, @RequestBody Rules rules) {
        return new ResponseEntity<>(rulesService.updateRules(id, rules), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление правил игры по их id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameRules(@PathVariable("id") Long id){
        rulesService.deleteRules(id);
        return ResponseEntity.ok().build();
    }
}
