package ru.gb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.backend.models.GameRules;
@Repository
public interface GameRulesRepository extends JpaRepository<GameRules, Long> {
}
