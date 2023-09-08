package ru.kata.spring.boot_security.demo.init;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DbInit {
    private final UserService userService;
    private final RoleService roleService;

    public DbInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

        User user = new User("zee@mail.ru", "zee", "zee", 33, Set.of(roleAdmin, roleUser), "zee", true);
        userService.saveUser(user);
    }
}
