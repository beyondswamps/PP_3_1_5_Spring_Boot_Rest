package ru.kata.spring.boot_security.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.exceptions.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDtoService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;
    private final UserDtoService userDtoService;

    public AdminRestController(UserService userService, UserDtoService userDtoService) {
        this.userService = userService;
        this.userDtoService = userDtoService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userDtoService.getUserDtoById(userId);
    }

    @PostMapping("/users/new")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestBody UserDto userDto) {
        userDtoService.saveUserDto(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/users/edit")
    public ResponseEntity<HttpStatus> editUser(@RequestBody UserDto userDto) {
        userDtoService.editUserDto(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/users/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
