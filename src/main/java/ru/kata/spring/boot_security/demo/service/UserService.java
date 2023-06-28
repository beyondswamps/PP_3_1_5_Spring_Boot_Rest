package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getUsers();

    User getUser(Long id);

    User getUserByUsername(String username);

    void updateUser(User userForm);

    void deleteUser(Long id);

    boolean updatePassword(User user, String oldPassword, String newPassword);
}
