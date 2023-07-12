package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
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
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        model.addAttribute("currentUser", currentUser);
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "addUser";
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
    public String listUsers(Model model, User user) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/edit")
    public String editUser(Model model,
                           @RequestParam Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "editUser";
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

    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
