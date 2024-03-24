package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.User;
import ru.gb.backend.repositories.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * Метод для получения списка всех пользователей
     *
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        log.info("IN getAllUsers - {} users found", result.size());
        return result;
    }

    /**
     * Метод для нахождения пользователя по username
     *
     * @param username
     * @return пользователя с переданным username
     */

    public User getUserByUsername(String username) {
        User result = userRepository.findByUsername(username).orElse(null);
        if (result == null) {
            log.warn("IN getUserByUsername - no user found by username: {}", username);
            return null;
        }
        log.info("IN getUserByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    /**
     * Метод для добавления пользователя в БД
     *
     * @param user
     * @return сохраненного пользователя
     */
    public User createUser(User user) {
        log.info("IN createUser - user: {} created", user);
        return userRepository.save(user);
    }

    /**
     * Метод для получения пользователя по id
     *
     * @param id
     * @return пользователя с заданным id или null
     */
    public User getUserById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN getUserById - no user found by id: {}", id);
            return null;
        }
        log.info("IN getUserById - user: {} found by id: {}", result, id);
        return result;
    }

    /**
     * Метод для редактирования данных пользователя
     *
     * @param user
     * @return отредактированного пользователя
     */
    public User updateUser(Long id, User user) {
        User userById = userRepository.findById(id).orElse(null);
        if (userById == null) {
            log.warn("IN updateUser - no user found by id: {}", id);
            return null;
        } else {
            userById.setUsername(user.getUsername());
            userById.setEmail(user.getEmail());
            userById.setPassword(passwordEncoder.encode(user.getPassword()));
            userById.setRole(user.getRole());
            userById.setChildSex(user.getChildSex());
            userById.setChildAge(user.getChildAge());
            log.info("IN updateUser - user: {} with id: {} updated", user, id);
            return userRepository.save(userById);
        }
    }

    /**
     * Метод для удаления пользователя по id
     *
     * @param id
     */
    public void deleteUser(Long id) {
        User userById = userRepository.findById(id).orElse(null);
        if (userById == null) {
            log.warn("IN deleteUser - no user found by id: {}", id);
        } else {
            userRepository.delete(userById);
            log.info("IN deleteUser - user with id: {} successfully deleted", id);
        }
    }
}
