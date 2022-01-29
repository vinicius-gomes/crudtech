package com.crudtech.cadastrin.controller;

import com.crudtech.cadastrin.exception.UserNotFoundException;
import com.crudtech.cadastrin.helper.UserHelper;
import com.crudtech.cadastrin.model.User;
import com.crudtech.cadastrin.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    private final UserHelper userHelper;

    UserController(UserService service, UserHelper userHelper) {
        this.userService = service;
        this.userHelper = userHelper;
    }

    @GetMapping("/users")
    List<User> all() {
        List<User> users = userService.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException();
        }
        return users;
    }

    @GetMapping("/users/{id}")
    User get(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException(id);
        }
        return user.get();
    }

    @PostMapping("/users")
    User post(@RequestBody User newUser) {
        return userService.create(newUser);
    }

    @PutMapping("/users/{id}")
    User put(@RequestBody User newUser, @PathVariable Long id) {
        return userService.findById(id).map(user -> {
            user.setEmail(newUser.getEmail());
            user.setUsername(newUser.getUsername());
            user.setName(newUser.getName());
            if (userHelper.isPasswordValid(newUser.getPassword())) {
                user.setPassword(newUser.getPassword());
            }
            return userService.save(user);
        }).orElseThrow(() -> {
            throw new UserNotFoundException(id);
        });
    }

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable Long id) {
        userService.findById(id).ifPresentOrElse(user -> {
            user.setActive(false);
            userService.save(user);
        }, () -> {
            throw new UserNotFoundException(id);
        });
    }
}
