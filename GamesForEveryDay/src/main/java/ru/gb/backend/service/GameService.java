package ru.gb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.ChildSex;
import ru.gb.backend.models.Game;
import ru.gb.backend.repository.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
private final GameRepository gameRepository;
    /**
     * Метод для получения списка всех игр
     * @return все игры
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Метод для добавления игры в БД
     * @param game
     * @return сохраненную игру
     */
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    /**
     * Метод для получения игры по id
     * @param id
     * @return игру с заданным id или null
     */
    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(null);
    }

    /**
     * Метод для редактирования игры
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
            return gameRepository.save(gameById);
        }  else {
            throw new IllegalArgumentException("Game not found with id: " + id);
        }
    }

    /**
     * Метод для удаления игры
     * @param id
     */
    public void deleteGame(Long id) {
        Game gameById = getGameById(id);
        gameRepository.delete(gameById);
    }


}
