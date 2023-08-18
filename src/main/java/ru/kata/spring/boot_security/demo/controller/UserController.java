package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void currentUser(Model model) {
        model.addAttribute("currentUser",
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal());
    }

    @GetMapping("/")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/user")
    public String showUserInfo(Model model) {
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userIsAdmin",
                currentUser.getAuthorities().stream().
                        map(role->role.getAuthority())
                        .collect(Collectors.toSet())
                        .contains("ROLE_ADMIN"));
        return "user";
    }

    @GetMapping("/changePassword")
    public String getChangePassword() {
        return "changePass";
    }

    @PostMapping("/changePassword")
    public String postChangePassword(@RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword) {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!userService.updatePassword(user, currentPassword, newPassword)) {
            return "changePass";
        }
        return "redirect:/user";
    }

}
