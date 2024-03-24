package ru.gb.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Locale;
@Data
@Entity
@Table(name = "schedule_for_a_week")
public class ScheduleForAWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @Column(name = "game_id", nullable = false)
    private Long gameId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "dayOfWeek: %s, game_id: %s",
                dayOfWeek, gameId);
    }
}
