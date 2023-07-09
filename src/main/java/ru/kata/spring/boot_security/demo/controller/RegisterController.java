package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final RoleService roleService;

    public RegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getRegisterForm(Model model,
                                  @ModelAttribute("user") User user) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "register";
    }

    @PostMapping
    public String sendRegisterForm(@ModelAttribute("user") User user,
                                   @RequestParam(value = "roles", defaultValue = "") List<Long> roles) {
        if (roles != null) {
            Set<Role> rolesSet = new HashSet<>();
            for (int i = 0; i < roles.size(); i++) {
                Role role = new Role();
                role.setId(roles.get(i));
                role.setName((roleService.findById(roles.get(i)).getName()));
                rolesSet.add(role);
            }
            user.setRoles(rolesSet);
        }

        userService.addUser(user);
        return "redirect:/";

    }
}
