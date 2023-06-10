package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        userService.addUser(user);
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String listUsers(Model model, User user) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", user);
        return "users";
    }

    @GetMapping(value="/edit")
    public String editUser(@RequestParam Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute User user, Model model, @RequestParam Long id) {
        model.addAttribute("user", user);
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/users/all";
    }

    @GetMapping(value="/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/users/all";
    }
}
