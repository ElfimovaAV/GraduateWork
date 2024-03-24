package ru.gb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.backend.models.DayOfWeek;
import ru.gb.backend.models.ScheduleForAWeek;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ScheduleForAWeekRepository extends JpaRepository<ScheduleForAWeek, Long> {
    Optional<List<ScheduleForAWeek>> findScheduleForAWeekByUserId(Long userId);
    ScheduleForAWeek findScheduleForAWeekByDayOfWeekAndUserId(DayOfWeek dayOfWeek, Long userId);
    void deleteAllByUserId(Long userId);
}
