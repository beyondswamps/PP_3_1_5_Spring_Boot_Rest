package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.GrantedAuthority;
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
                                   @RequestParam(value = "roles") long[] roles) {
        if (roles != null) {
            Set<Role> rolesSet = new HashSet<>();
            for (int i = 0; i < roles.length; i++) {
                Role role = new Role();
                role.setId(roles[i]);
                role.setName((roleService.findById(roles[i]).getName()));
                rolesSet.add(role);
            }
            user.setAuthorities(rolesSet);
        }

        userService.addUser(user);
        return "redirect:/";

    }
}
