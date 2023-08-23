package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<User> getUsers();

    User getUser(Long id);

    User getUserByEmail(String email);

    void updateUser(User userForm);

    void deleteUser(Long id);

    boolean updatePassword(User user, String oldPassword, String newPassword);

    boolean updateCurrentUserPassword(String currentPassword, String newPassword);
}
