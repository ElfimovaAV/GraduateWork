package ru.gb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.backend.models.Rules;
@Repository
public interface RulesRepository extends JpaRepository<Rules, Long> {
}
