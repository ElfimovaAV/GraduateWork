package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.*;
import ru.gb.backend.repositories.GameRepository;
import ru.gb.backend.repositories.ScheduleForAWeekRepository;
import ru.gb.backend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ScheduleForAWeekRepository scheduleForAWeekRepository;

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
        log.info("IN createGame - new game: {} created", game);
        return gameRepository.save(game);
    }

    /**
     * Метод для получения игры по id
     *
     * @param id
     * @return игру с заданным id или null
     */
    public Game getGameById(Long id) {
        Game result = gameRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN getGameById - no game found by id: {}", id);
            return null;
        }
        log.info("IN getGameById - found game: {} with id: {}", result, id);
        return result;
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
            log.info("IN updateGame - game: {} with id: {} updated", gameById, id);
            return gameRepository.save(gameById);
        } else {
            log.warn("IN updateGame - no game with id: {}", id);
            return null;
        }
    }

    /**
     * Метод для удаления игры
     *
     * @param id
     */
    public void deleteGame(Long id) {
        Game result = gameRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN deleteGame - no game found by id: {}", id);
        } else {
            log.info("IN deleteGame - game: {} deleted", result);
            gameRepository.delete(result);
        }
    }

    /**
     * Метод для подбора списка игр по полу ребенка пользователя
     *
     * @param userId
     * @return список игр, подходящих по полу ребенку пользователя
     */
    public List<Game> getGamesByChildSex(Long userId) {
        List<Game> suitableGames = gameRepository.findByChildSex(ChildSex.ALL);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("IN getGamesByChildSex - no user found by id: {}", userId);
            return null;
        } else {
            ChildSex childSex = user.getChildSex();
            if (childSex == ChildSex.BOY) {
                suitableGames.addAll(gameRepository.findByChildSex(ChildSex.BOY));
            } else {
                suitableGames.addAll(gameRepository.findByChildSex(ChildSex.GIRL));
            }
            log.info("IN getGamesByChildSex - user: {} got suitable games by child sex: {}", user, childSex);
            return suitableGames;
        }
    }

    /**
     * Метод для подбора списка игр по возрасту ребенку пользователя
     *
     * @param userId
     * @return список игр, подходящих по возрасту ребенку пользователя
     */
    public List<Game> getGamesByChildAge(Long userId) {
        List<Game> suitableGamesFrom = new ArrayList<>();
        List<Game> suitableGamesTo = new ArrayList<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("IN getGamesByChildAge - no user found by id: {}", userId);
            return null;
        } else {
            int childAge = user.getChildAge();
            suitableGamesFrom.addAll(gameRepository.findByAgeFromIsLessThanEqual(childAge));
            suitableGamesTo.addAll(gameRepository.findByAgeToIsGreaterThanEqual(childAge));
            log.info("IN getGamesByChildAge - user: {} got suitable games by child age: {}", user, childAge);
            return intersection(suitableGamesFrom, suitableGamesTo);
        }
    }

    /**
     * Метод для подбора списка игр по полу и возрасту ребенка пользователя
     *
     * @param userId
     * @return список игр, подходящих по полу и возрасту ребенку пользователя
     */
    public List<Game> getGamesByChildSexAndAge(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("IN getGamesByChildAge - no user found by id: {}", userId);
            return null;
        } else {
            List<Game> suitableGamesByChildSex = getGamesByChildSex(userId);
            List<Game> suitableGamesByChildAge = getGamesByChildAge(userId);
            log.info("IN getGamesByChildSexAndAge - user: {} got suitable games by child sex: {} and age: {}",
                    user, user.getChildSex(), user.getChildAge());
            return intersection(suitableGamesByChildAge, suitableGamesByChildSex);
        }
    }

    /**
     * Метод для составления недельного расписания на основе подходящих по полу и возрасту ребенка пользователя игр
     *
     * @param userId
     * @return недельное расписание
     */
    public List<ScheduleForAWeek> createScheduleForAWeek(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("IN getGamesByChildAge - no user found by id: {}", userId);
            return null;
        } else {
            List<ScheduleForAWeek> scheduleForAWeeks = scheduleForAWeekRepository
                    .findScheduleForAWeekByUserId(userId).orElseThrow(null);
            if (scheduleForAWeeks.isEmpty()) {
                List<Game> suitableGames = getGamesByChildSexAndAge(userId);
                for (int i = 0; i < DayOfWeek.daysGetLength(); i++) {
                    ScheduleForAWeek scheduleForAWeek = new ScheduleForAWeek();
                    scheduleForAWeek.setDayOfWeek(DayOfWeek.getDayOfWeek(i));
                    scheduleForAWeek.setGameId(suitableGames.get(i).getId());
                    scheduleForAWeek.setUserId(userId);
                    scheduleForAWeekRepository.save(scheduleForAWeek);
                }
                log.info("IN createScheduleForAWeek - user: {} got schedule of suitable game for a week: {}",
                        user, scheduleForAWeekRepository.findScheduleForAWeekByUserId(userId).orElse(null));
                return scheduleForAWeekRepository.findScheduleForAWeekByUserId(userId).orElse(null);
            } else {
                log.warn("IN createScheduleForAWeek - user: {}  already has a schedule of suitable game for a week: {}",
                        user, scheduleForAWeeks);
                return null;
            }
        }
    }

    /**
     * Вспомогательный метод для получения списка игр по пересечению двух списков
     *
     * @param list1
     * @param list2
     * @return список game
     */

    private List<Game> intersection(List<Game> list1, List<Game> list2) {
        List<Game> list = new ArrayList<>();
        for (Game game : list1) {
            if (list2.contains(game)) {
                list.add(game);
            }
        }
        return list;
    }


}
