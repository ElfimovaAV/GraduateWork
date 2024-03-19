package ru.gb.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Locale;


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
    @Column(nullable = false)
    private String rules;

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "title: %s",
                title);
    }

}
