package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    List<Role> getRolesByIds(List<Long> ids);

    Role findByName(String name);

    void saveRole(Role role);

    Role findById(Long id);

    List<Long> rolesToIds(Set<Role> roles);
}
