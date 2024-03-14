package ru.gb.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String email;
    @Column(name = "child_sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChildSex childSex;
    @Column(name = "child_age", nullable = false)
    private int childAge;
}
