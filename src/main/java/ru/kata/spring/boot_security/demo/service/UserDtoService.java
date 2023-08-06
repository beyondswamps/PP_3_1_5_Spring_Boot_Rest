package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserDtoService {
    User fromDto(UserDto userDto);

    UserDto toDto(User user);

    UserDto getUserDtoById(Long id);

    void saveUserDto(UserDto userDto);

    void editUserDto(UserDto userDto);
}
