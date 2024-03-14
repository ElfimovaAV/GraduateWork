package ru.gb.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "game_rules")
public class GameRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String rule;
    @Column(name = "game_id")
    private Long gameId;

}
