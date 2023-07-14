package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute
    public void currentUser(Model model) {
        model.addAttribute("currentUser",
                (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
    }

    @PostMapping("/add")
    @Transactional
    public String addUser(@ModelAttribute User user,
                          @RequestParam List<Long> selectedRoles) {
        user.setRoles(Set.copyOf(roleService.getRolesByIds(selectedRoles)));
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("eachUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users";
    }

    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute User userForm,
                           @RequestParam(name = "id") Long id,
                           @RequestParam(name = "selectedRoles", defaultValue = "") List<Long> selectedRoles) {
        userForm.setId(id);
        userForm.setRoles(Set.copyOf(roleService.getRolesByIds(selectedRoles)));
        userService.updateUser(userForm);
        return "redirect:/admin/";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
