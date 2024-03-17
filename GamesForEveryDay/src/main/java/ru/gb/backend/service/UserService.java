package ru.gb.backend.service;

import ru.gb.backend.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User findByUsername(String username);
    User getUserById(Long id);
    void deleteUser(Long id);
}
