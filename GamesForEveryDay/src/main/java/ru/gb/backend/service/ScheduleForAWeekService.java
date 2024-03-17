package ru.gb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.ScheduleForAWeek;
import ru.gb.backend.repository.ScheduleForAWeekRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleForAWeekService {
    private final ScheduleForAWeekRepository scheduleForAWeekRepository;
    /**
     * Метод для получения списка всех расписаний по дням недели
     * @return все расписания по дням недели
     */
    public List<ScheduleForAWeek> getAllScheduleForAWeek() {
        return scheduleForAWeekRepository.findAll();
    }

    /**
     * Метод для добавления расписания по дням недели в БД
     * @param scheduleForAWeek
     * @return сохраненное расписание по дням недели
     */
    public ScheduleForAWeek createScheduleForAWeek(ScheduleForAWeek scheduleForAWeek) {
        return scheduleForAWeekRepository.save(scheduleForAWeek);
    }

    /**
     * Метод для получения расписания по дням недели по id
     * @param id
     * @return расписание по дням недели с заданным id или null
     */
    public ScheduleForAWeek getScheduleForAWeekById(Long id) {
        return scheduleForAWeekRepository.findById(id).orElseThrow(null);
    }

    /**
     * Метод для получения расписания по дням недели по email пользователя
     * @param id
     * @return расписание по дням недели с заданным email пользователя или null
     */
    public List<ScheduleForAWeek> getScheduleForAWeekByUserId(Long id) {
        return scheduleForAWeekRepository.findScheduleForAWeekByUserId(id);
    }

    /**
     * Метод для редактирования расписания по дням недели
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
            return scheduleForAWeekRepository.save(scheduleForAWeekById);
        }  else {
            throw new IllegalArgumentException("ScheduleForAWeek not found with id: " + id);
        }
    }

    /**
     * Метод для удаления расписания по дням недели
     * @param id
     */
    public void deleteScheduleForAWeek(Long id) {
        ScheduleForAWeek scheduleForAWeekById = getScheduleForAWeekById(id);
        scheduleForAWeekRepository.delete(scheduleForAWeekById);
    }
}
