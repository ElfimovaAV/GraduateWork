package ru.gb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.GameRules;
import ru.gb.backend.repository.GameRulesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameRulesService {
    private final GameRulesRepository gameRulesRepository;
    /**
     * Метод для получения списка всех правил игр
     * @return все правила игр
     */
    public List<GameRules> getAllGameRules() {
        return gameRulesRepository.findAll();
    }

    /**
     * Метод для добавления правил игры в БД
     * @param gameRules
     * @return сохраненные правила игры
     */
    public GameRules createGameRules(GameRules gameRules) {
        return gameRulesRepository.save(gameRules);
    }

    /**
     * Метод для получения правил игры по id
     * @param id
     * @return правила игры с заданным id или null
     */
    public GameRules getGameRulesById(Long id) {
        return gameRulesRepository.findById(id).orElseThrow(null);
    }

    /**
     * Метод для редактирования правил игры
     * @param gameRules
     * @return отредактированные правила игры
     */
    public GameRules updateGameRules(Long id, GameRules gameRules) {
        Optional<GameRules> optionalGameRules = gameRulesRepository.findById(id);
        if (optionalGameRules.isPresent()) {
            GameRules gameRulesById = optionalGameRules.get();
            gameRulesById.setRule(gameRules.getRule());
            gameRulesById.setGameId(gameRules.getGameId());
            return gameRulesRepository.save(gameRulesById);
        }  else {
            throw new IllegalArgumentException("GameRules not found with id: " + id);
        }
    }

    /**
     * Метод для удаления правил игры
     * @param id
     */
    public void deleteGameRules(Long id) {
        GameRules gameRulesById = getGameRulesById(id);
        gameRulesRepository.delete(gameRulesById);
    }
}
