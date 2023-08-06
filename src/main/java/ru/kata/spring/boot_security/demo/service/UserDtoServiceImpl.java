package ru.kata.spring.boot_security.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.exceptions.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

@Service
public class UserDtoServiceImpl implements UserDtoService {

    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserDtoServiceImpl(RoleService roleService, UserService userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public User fromDto(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRoles(Set.copyOf(roleService.getRolesByIds(userDto.getRolesIds())));
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRolesIds(roleService.rolesToIds(user.getRoles()));
        return userDto;
    }

    @Override
    public UserDto getUserDtoById(Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new UserNotFoundException("Error! There is no user with id: " + id);
        }
        return this.toDto(user);
    }

    @Override
    public void saveUserDto(UserDto userDto) {
        userService.saveUser(this.fromDto(userDto));
    }

    @Override
    public void editUserDto(UserDto userDto) {
        userService.updateUser(this.fromDto(userDto));
    }
}
