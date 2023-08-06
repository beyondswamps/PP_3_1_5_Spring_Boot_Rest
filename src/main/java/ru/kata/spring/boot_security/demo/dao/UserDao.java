package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void saveUser(User user);

    List<User> getUsers();

    User getUser(Long id);

    void deleteUser(Long id);

    void updateUser(User user);

    User getUserByEmail(String email);
}
