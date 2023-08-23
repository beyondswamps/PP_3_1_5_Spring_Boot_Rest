package ru.kata.spring.boot_security.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.exceptions.ErrorEntity;
import ru.kata.spring.boot_security.demo.exceptions.UserNotFoundException;
import ru.kata.spring.boot_security.demo.exceptions.WrongPasswordException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDtoService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AdminRestController {

    private final UserService userService;
    private final UserDtoService userDtoService;

    public AdminRestController(UserService userService, UserDtoService userDtoService) {
        this.userService = userService;
        this.userDtoService = userDtoService;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userDtoService.getUserDtoById(userId);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestBody @Valid UserDto userDto) {
        userDtoService.saveUserDto(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<HttpStatus> editUser(@RequestBody UserDto userDto) {
        userDtoService.editUserDto(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorEntity> handleException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(new ErrorEntity(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException methodArgumentNotValidException, BindingResult bindingResult) {
        Map<String, String> errorsMap = bindingResult
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorEntity> handleException(WrongPasswordException wrongPasswordException) {
        return new ResponseEntity<>(new ErrorEntity(wrongPasswordException.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
