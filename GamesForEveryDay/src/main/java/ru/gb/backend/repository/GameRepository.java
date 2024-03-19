package ru.gb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.backend.models.ChildSex;
import ru.gb.backend.models.Game;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByChildSex(ChildSex childSex);
    List<Game> findByAgeFromIsLessThanEqual(int ageFrom);
    List<Game> findByAgeToIsGreaterThanEqual(int ageTo);


}
