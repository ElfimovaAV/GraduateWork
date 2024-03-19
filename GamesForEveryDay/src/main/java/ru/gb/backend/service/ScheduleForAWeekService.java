package ru.gb.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.backend.exceptions.MyIllegalArgumentException;
import ru.gb.backend.models.ScheduleForAWeek;
import ru.gb.backend.repository.ScheduleForAWeekRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleForAWeekService {
    private final ScheduleForAWeekRepository scheduleForAWeekRepository;

    /**
     * Метод для получения списка всех расписаний по дням недели
     *
     * @return все расписания по дням недели
     */
    public List<ScheduleForAWeek> getAllScheduleForAWeek() {
        List<ScheduleForAWeek> result = scheduleForAWeekRepository.findAll();
        log.info("IN getAllScheduleForAWeek - found {} schedules for a week", result.size());
        return result;
    }

    /**
     * Метод для добавления расписания по дням недели в БД
     *
     * @param scheduleForAWeek
     * @return сохраненное расписание по дням недели
     */
    public ScheduleForAWeek createScheduleForAWeek(ScheduleForAWeek scheduleForAWeek) {
        log.info("IN createScheduleForAWeek - created new schedule for a week: {}", scheduleForAWeek);
        return scheduleForAWeekRepository.save(scheduleForAWeek);
    }

    /**
     * Метод для получения расписания по дням недели по id
     *
     * @param id
     * @return расписание по дням недели с заданным id или null
     */
    public ScheduleForAWeek getScheduleForAWeekById(Long id) {
        log.info("IN getScheduleForAWeekById - found schedule for a week with id: {}", id);
        return scheduleForAWeekRepository.findById(id).orElseThrow(null);
    }

    /**
     * Метод для получения расписания по дням недели по id пользователя
     *
     * @param userId
     * @return расписание по дням недели с заданным email пользователя или null
     */
    public List<ScheduleForAWeek> getScheduleForAWeekByUserId(Long userId) {
        try {
            log.info("IN getScheduleForAWeekByUserId - found schedule for a week with userId: {}", userId);
            return scheduleForAWeekRepository.findScheduleForAWeekByUserId(userId);
        } catch (IllegalArgumentException e) {
            throw new MyIllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Метод для редактирования расписания по дням недели
     *
     * @param scheduleForAWeek
     * @return отредактированное расписание по дням недели
     */
    public ScheduleForAWeek updateScheduleForAWeek(Long id, ScheduleForAWeek scheduleForAWeek) {
        Optional<ScheduleForAWeek> optionalScheduleForAWeek = scheduleForAWeekRepository.findById(id);
        if (optionalScheduleForAWeek.isPresent()) {
            ScheduleForAWeek scheduleForAWeekById = optionalScheduleForAWeek.get();
            scheduleForAWeekById.setDayOfWeek(scheduleForAWeek.getDayOfWeek());
            scheduleForAWeekById.setGameId(scheduleForAWeek.getGameId());
            scheduleForAWeekById.setUserId(scheduleForAWeek.getUserId());
            log.info("IN updateScheduleForAWeek - updated schedule for a week with id: {}", id);
            return scheduleForAWeekRepository.save(scheduleForAWeekById);
        } else {
            throw new IllegalArgumentException("ScheduleForAWeek not found with id: " + id);
        }
    }

    /**
     * Метод для удаления расписания по дням недели
     *
     * @param userId
     */
    public void deleteScheduleForAWeekByUserId(Long userId) {
        try {
            log.info("IN deleteScheduleForAWeekByUserId - deleted schedule for a week with userId: {}", userId);
            scheduleForAWeekRepository.deleteAllByUserId(userId);
        } catch (IllegalArgumentException e) {
            throw new MyIllegalArgumentException(e.getMessage());
        }
    }


}
