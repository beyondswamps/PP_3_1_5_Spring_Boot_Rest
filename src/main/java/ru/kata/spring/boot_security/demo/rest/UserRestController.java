package ru.kata.spring.boot_security.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserDtoService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping
public class UserRestController {

    private final UserDtoService userDtoService;

    private final UserService userService;

    public UserRestController(UserDtoService userDtoService, UserService userService) {
        this.userDtoService = userDtoService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid UserDto userDto) {
        userDtoService.saveUserDto(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/updatepass")
    public ResponseEntity<HttpStatus> updatePassword(@RequestBody Map<String, String> passwords) {
        userService.updateCurrentUserPassword(passwords.get("currentPassword"), passwords.get("newPassword"));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
