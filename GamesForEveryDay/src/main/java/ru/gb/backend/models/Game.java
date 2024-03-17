package ru.gb.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rules_id", referencedColumnName = "id")
    private Rules rules;

}
