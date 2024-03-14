package ru.gb.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "schedule_for_a_week")
public class ScheduleForAWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @Column(name = "game_id")
    private Long gameId;
    @Column(name = "user_email")
    private String userEmail;
}
