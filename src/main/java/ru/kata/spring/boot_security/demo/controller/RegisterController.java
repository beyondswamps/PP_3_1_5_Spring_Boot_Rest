package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;
    private RoleService roleService;

    public RegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getRegisterForm(Model model,
                                  @ModelAttribute("user") User user) {
        List<Long> selectedRoles = Collections.emptyList();
        model.addAttribute("selectedRoles", selectedRoles);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "register";
    }

    @PostMapping
    public String sendRegisterForm(Model model,
                                   @ModelAttribute("user") User user,
                                   @RequestParam("selectedRolesIds") Long[] selectedRolesIds) {
//        user.setAuthorities(userRoles);
//        modelassignRoles = roleService.getRoles(selectedRoles);
//        user.setAuthorities(assignRoles.stream().collect(Collectors.toSet()));
        userService.addUser(user);
        return "redirect:/";

    }
}
