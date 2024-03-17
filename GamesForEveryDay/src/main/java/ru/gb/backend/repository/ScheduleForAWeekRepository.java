package ru.gb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.backend.models.ScheduleForAWeek;

import java.util.List;

@Repository
public interface ScheduleForAWeekRepository extends JpaRepository<ScheduleForAWeek, Long> {
    List<ScheduleForAWeek> findScheduleForAWeekByUserId(Long id);
}
