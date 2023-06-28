package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showUserInfo(Model model) {
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        model.addAttribute("currentUser", currentUser);
        return "user";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/changePassword")
    public String getChangePassword() {
        return "changePass";
    }

    @PostMapping("/changePassword")
    public String postChangePassword(Model model,
                                     @RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword) {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

//        model.addAttribute("currentPassword", currentPassword);
//        model.addAttribute("newPassword", newPassword);
        if (!userService.updatePassword(user, currentPassword, newPassword)) {
            return "changePass";
        }
        return "redirect:/user";
    }

}
