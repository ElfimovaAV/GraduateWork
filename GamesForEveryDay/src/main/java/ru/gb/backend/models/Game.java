package ru.gb.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(name = "child_sex")
    @Enumerated(EnumType.STRING)
    private ChildSex childSex;
    @Column(name = "age_from")
    private int ageFrom;
    @Column(name = "age_to")
    private int ageTo;

}