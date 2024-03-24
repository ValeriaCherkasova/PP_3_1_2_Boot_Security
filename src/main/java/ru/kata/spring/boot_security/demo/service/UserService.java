package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    void deleteUser(Long userId);

    List<User> getAllUsers();

    User findByUsername(String name);

    void updateUser(User user);

    Optional<User> findById(Long id);
}
