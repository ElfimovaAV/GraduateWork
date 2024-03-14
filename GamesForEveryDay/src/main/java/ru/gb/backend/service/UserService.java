package ru.gb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.*;
import ru.gb.backend.repository.GameRepository;
import ru.gb.backend.repository.ScheduleForAWeekRepository;
import ru.gb.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ScheduleForAWeekRepository scheduleForAWeekRepository;

    /**
     * Метод для получения списка всех пользователей
     *
     * @return все пользователи
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Метод для добавления пользователя в БД
     *
     * @param user
     * @return сохраненного пользователя
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Метод для получения пользователя по id
     *
     * @param id
     * @return пользователя с заданным id или null
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(null);
    }

    /**
     * Метод для редактирования пользователя
     *
     * @param user
     * @return отредактированного пользователя
     */
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userById = optionalUser.get();
            userById.setLogin(user.getLogin());
            userById.setEmail(user.getEmail());
            userById.setChildSex(user.getChildSex());
            userById.setChildAge(user.getChildAge());
            return userRepository.save(userById);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }

    /**
     * Метод для удаления пользователя
     *
     * @param id
     */
    public void deleteUser(Long id) {
        User userById = getUserById(id);
        userRepository.delete(userById);
    }

    /**
     * Метод для подбора списка игр по полу ребенка пользователя
     *
     * @param id
     * @return список игр, подходящих по полу ребенку пользователя
     */
    public List<Game> getGamesByChildSex(Long id) {
        List<Game> suitableGames = gameRepository.findByChildSex(ChildSex.ALL);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userById = optionalUser.get();
            ChildSex childSex = userById.getChildSex();
            if (childSex == ChildSex.BOY) {
                suitableGames.addAll(gameRepository.findByChildSex(ChildSex.BOY));
            } else {
                suitableGames.addAll(gameRepository.findByChildSex(ChildSex.GIRL));
            }
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        return suitableGames;
    }

    /**
     * Метод для подбора списка игр по возрасту ребенка пользователя
     *
     * @param id
     * @return список игр, подходящих по возрасту ребенку пользователя
     */
    public List<Game> getGamesByChildAge(Long id) {
        List<Game> suitableGamesFrom = new ArrayList<>();
        List<Game> suitableGamesTo = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userById = optionalUser.get();
            int childAge = userById.getChildAge();
            suitableGamesFrom.addAll(gameRepository.findByAgeFromIsLessThanEqual(childAge));
            suitableGamesTo.addAll(gameRepository.findByAgeToIsGreaterThanEqual(childAge));
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        return intersection(suitableGamesFrom, suitableGamesTo);
    }

    /**
     * Метод для подбора списка игр по полу ивозрасту ребенка пользователя
     *
     * @param id
     * @return список игр, подходящих по полу и возрасту ребенку пользователя
     */
    public List<Game> getGamesByChildSexAndAge(Long id) {
        List<Game> suitableGames = gameRepository.findByChildSex(ChildSex.ALL);
        List<Game> suitableGamesFrom = new ArrayList<>();
        List<Game> suitableGamesTo = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userById = optionalUser.get();
            ChildSex childSex = userById.getChildSex();
            if (childSex == ChildSex.BOY) {
                suitableGames.addAll(gameRepository.findByChildSex(ChildSex.BOY));
            } else {
                suitableGames.addAll(gameRepository.findByChildSex(ChildSex.GIRL));
            }
            int childAge = userById.getChildAge();
            suitableGamesFrom.addAll(gameRepository.findByAgeFromIsLessThanEqual(childAge));
            suitableGamesTo.addAll(gameRepository.findByAgeToIsGreaterThanEqual(childAge));
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        return intersection(intersection(suitableGamesFrom, suitableGamesTo), suitableGames);
    }

    public List<Game> intersection(List<Game> list1, List<Game> list2) {
        List<Game> list = new ArrayList<>();
        for (Game game : list1) {
            if (list2.contains(game)) {
                list.add(game);
            }
        }
        return list;
    }

    public List<ScheduleForAWeek> createScheduleForAWeek(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userById = optionalUser.get();
            List<Game> suitableGames = getGamesByChildSexAndAge(id);
            ScheduleForAWeek scheduleForAMonday = new ScheduleForAWeek();
            scheduleForAMonday.setDayOfWeek(DayOfWeek.MONDAY);
            scheduleForAMonday.setUserEmail(userById.getEmail());
            scheduleForAMonday.setGameId(suitableGames.get(0).getId());
            scheduleForAWeekRepository.save(scheduleForAMonday);
            ScheduleForAWeek scheduleForTuesday = new ScheduleForAWeek();
            scheduleForTuesday.setDayOfWeek(DayOfWeek.TUESDAY);
            scheduleForTuesday.setUserEmail(userById.getEmail());
            scheduleForTuesday.setGameId(suitableGames.get(1).getId());
            scheduleForAWeekRepository.save(scheduleForTuesday);
            ScheduleForAWeek scheduleForWednesday = new ScheduleForAWeek();
            scheduleForWednesday.setDayOfWeek(DayOfWeek.WEDNESDAY);
            scheduleForWednesday.setUserEmail(userById.getEmail());
            scheduleForWednesday.setGameId(suitableGames.get(2).getId());
            scheduleForAWeekRepository.save(scheduleForWednesday);
            ScheduleForAWeek scheduleForThursday = new ScheduleForAWeek();
            scheduleForThursday.setDayOfWeek(DayOfWeek.THURSDAY);
            scheduleForThursday.setUserEmail(userById.getEmail());
            scheduleForThursday.setGameId(suitableGames.get(3).getId());
            scheduleForAWeekRepository.save(scheduleForThursday);
            ScheduleForAWeek scheduleForFriday = new ScheduleForAWeek();
            scheduleForFriday.setDayOfWeek(DayOfWeek.FRIDAY);
            scheduleForFriday.setUserEmail(userById.getEmail());
            scheduleForFriday.setGameId(suitableGames.get(4).getId());
            scheduleForAWeekRepository.save(scheduleForFriday);
            ScheduleForAWeek scheduleForSaturday = new ScheduleForAWeek();
            scheduleForSaturday.setDayOfWeek(DayOfWeek.SATURDAY);
            scheduleForSaturday.setUserEmail(userById.getEmail());
            scheduleForSaturday.setGameId(suitableGames.get(5).getId());
            scheduleForAWeekRepository.save(scheduleForSaturday);
            ScheduleForAWeek scheduleForSunday = new ScheduleForAWeek();
            scheduleForSunday.setDayOfWeek(DayOfWeek.SUNDAY);
            scheduleForSunday.setUserEmail(userById.getEmail());
            scheduleForSunday.setGameId(suitableGames.get(6).getId());
            scheduleForAWeekRepository.save(scheduleForSunday);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        } return scheduleForAWeekRepository.findAll();
    }

}
