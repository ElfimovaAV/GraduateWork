package ru.gb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.backend.models.DayOfWeek;
import ru.gb.backend.models.Game;
import ru.gb.backend.models.ScheduleForAWeek;

import java.util.List;

@Repository
@Transactional
public interface ScheduleForAWeekRepository extends JpaRepository<ScheduleForAWeek, Long> {
    List<ScheduleForAWeek> findScheduleForAWeekByUserId(Long userId);
    ScheduleForAWeek findScheduleForAWeekByDayOfWeekAndUserId(DayOfWeek dayOfWeek, Long userId);
    void deleteAllByUserId(Long id);
}
