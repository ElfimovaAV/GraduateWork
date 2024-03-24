package ru.gb.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.backend.exceptions.MyIllegalArgumentException;
import ru.gb.backend.models.User;
import ru.gb.backend.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * Метод для получения списка всех пользователей
     *
     * @return все пользователи
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

    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username).orElse(null);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
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
        log.info("IN findByUserId - user: {} found by id: {}", result, id);
        return result;
    }

    /**
     * Метод для редактирования пользователя
     *
     * @param user
     * @return отредактированного пользователя
     */
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userById = optionalUser.get();
            userById.setUsername(user.getUsername());
            userById.setEmail(user.getEmail());
            userById.setPassword(passwordEncoder.encode(user.getPassword()));
            userById.setRole(user.getRole());
            userById.setChildSex(user.getChildSex());
            userById.setChildAge(user.getChildAge());
            log.info("IN updateUser - user: {} with id: {} updated", user, id);
            return userRepository.save(userById);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }

    /**
     * Метод для удаления пользователя по id
     *
     * @param id
     */
    public void deleteUser(Long id) {
        try {
            User userById = getUserById(id);
            userRepository.delete(userById);
            log.info("IN deleteUser - user with id: {} successfully deleted", id);
        } catch (IllegalArgumentException e) {
            throw new MyIllegalArgumentException(e.getMessage());
        }
    }

}
