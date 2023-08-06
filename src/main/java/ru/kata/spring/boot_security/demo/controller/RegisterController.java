package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;


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
                                   @RequestParam(value = "selectedRoles", defaultValue = "") List<Long> selectedRoles) {
        user.setRoles(Set.copyOf(roleService.getRolesByIds(selectedRoles)));
        userService.saveUser(user);
        return "redirect:/";
    }
}
