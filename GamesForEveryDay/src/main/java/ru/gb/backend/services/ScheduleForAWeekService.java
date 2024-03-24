package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.DayOfWeek;
import ru.gb.backend.models.Game;
import ru.gb.backend.models.ScheduleForAWeek;
import ru.gb.backend.repositories.GameRepository;
import ru.gb.backend.repositories.ScheduleForAWeekRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleForAWeekService {
    private final ScheduleForAWeekRepository scheduleForAWeekRepository;
    private final GameRepository gameRepository;


    /**
     * Метод для получения расписания по дням недели по id пользователя
     *
     * @param userId
     * @return расписание по дням недели с заданным email пользователя или null
     */
    public List<ScheduleForAWeek> getScheduleForAWeekByUserId(Long userId) {
        List<ScheduleForAWeek> result = scheduleForAWeekRepository
                .findScheduleForAWeekByUserId(userId).orElseThrow(null);
        if (result.isEmpty()) {
            log.warn("IN getScheduleForAWeekByUserId - no schedule for a week by userId: {}", userId);
            return null;
        }
        log.info("IN getScheduleForAWeekByUserId - found schedule for a week: {} with id: {}", result, userId);
        return result;
    }


    /**
     * Метод для удаления недельного расписания пользователя
     *
     * @param userId
     */
    public void deleteScheduleForAWeekByUserId(Long userId) {
        List<ScheduleForAWeek> result = scheduleForAWeekRepository
                .findScheduleForAWeekByUserId(userId).orElseThrow(null);
        if (result.isEmpty()) {
            log.warn("IN deleteScheduleForAWeekByUserId - no schedule for a week by userId: {}", userId);
        } else {
            log.info("IN deleteScheduleForAWeekByUserId - deleted schedule for a week: {} with userId: {}", result, userId);
            scheduleForAWeekRepository.deleteAllByUserId(userId);
        }
    }

    /**
     * Метод для получения игры из недельного расписания на переданный в параметрах день недели
     *
     * @param dayOfWeek
     * @param userId
     * @return game
     */
    public Optional<Game> getGameForToday(DayOfWeek dayOfWeek, Long userId) {
        List<ScheduleForAWeek> result = scheduleForAWeekRepository
                .findScheduleForAWeekByUserId(userId).orElseThrow(null);
        if (result.isEmpty()) {
            log.warn("IN getGameForToday - no schedule for a week by userId: {}", userId);
            return Optional.empty();
        } else {
            ScheduleForAWeek scheduleForAWeek = scheduleForAWeekRepository.findScheduleForAWeekByDayOfWeekAndUserId(dayOfWeek, userId);
            log.info("IN getGameForToday - user with id: {} got games: {} for dayOfWeeK: {}", userId, gameRepository.findById(scheduleForAWeek.getGameId()).orElse(null), dayOfWeek);
            return gameRepository.findById(scheduleForAWeek.getGameId());
        }
    }
}
