package ru.gb.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.backend.exceptions.MyIllegalArgumentException;
import ru.gb.backend.models.Game;
import ru.gb.backend.repository.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameRepository gameRepository;

    /**
     * Метод для получения списка всех игр
     *
     * @return все игры
     */
    public List<Game> getAllGames() {
        List<Game> result = gameRepository.findAll();
        log.info("IN getAllGames - found {} games", result.size());
        return result;
    }

    /**
     * Метод для добавления игры в БД
     *
     * @param game
     * @return сохраненную игру
     */
    public Game createGame(Game game) {
        log.info("IN createGame - create new game: {}", game);
        return gameRepository.save(game);
    }

    /**
     * Метод для получения игры по id
     *
     * @param id
     * @return игру с заданным id или null
     */
    public Game getGameById(Long id) {
        log.info("IN getGameById - found game with id: {}", id);
        return gameRepository.findById(id).orElseThrow(null);
    }

    /**
     * Метод для редактирования игры
     *
     * @param game
     * @return отредактированную игру
     */
    public Game updateGame(Long id, Game game) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game gameById = optionalGame.get();
            gameById.setTitle(game.getTitle());
            gameById.setDescription(game.getDescription());
            gameById.setChildSex(game.getChildSex());
            gameById.setAgeFrom(game.getAgeFrom());
            gameById.setAgeTo(game.getAgeTo());
            gameById.setRules(game.getRules());
            log.info("IN updateGame - game with id: {} updated", gameById);
            return gameRepository.save(gameById);
        } else {
            throw new IllegalArgumentException("Game not found with id: " + id);
        }
    }

    /**
     * Метод для удаления игры
     *
     * @param id
     */
    public void deleteGame(Long id) {
        try {
            Game gameById = getGameById(id);
            log.info("IN deleteGame - game with id: {} deleted", id);
            gameRepository.delete(gameById);
        } catch (IllegalArgumentException e) {
            throw new MyIllegalArgumentException(e.getMessage());
        }
    }


}
