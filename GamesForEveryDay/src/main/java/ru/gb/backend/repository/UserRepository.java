package ru.gb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.backend.models.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
