package com.crudtech.cadastrin.controller;

import com.crudtech.cadastrin.exception.UserNotFoundException;
import com.crudtech.cadastrin.exception.UserValidationException;
import com.crudtech.cadastrin.model.User;
import com.crudtech.cadastrin.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/users")
@RestController
@Validated
public class UserController {

    private final UserService userService;

    UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping
    List<User> all() {
        List<User> users = userService.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException();
        }
        return users;
    }

    @GetMapping("/{id}")
    User get(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException(id);
        }
        return user.get();
    }

    @PostMapping
    User post(@Valid @RequestBody User newUser, BindingResult result) {
        if(result.hasErrors()){
            throw new UserValidationException(result.getFieldErrors().stream().map(error -> error.getField() + " :" + error.getDefaultMessage()).collect(Collectors.joining(",")));
        }
        return userService.create(newUser);
    }

    @PutMapping("/{id}")
    User put(@Valid @RequestBody User newUser, @PathVariable Long id, BindingResult result) {
        if(result.hasErrors()){
            throw new UserValidationException(result.getFieldErrors().stream().map(error -> error.getField() + " :" + error.getDefaultMessage()).collect(Collectors.joining(",")));
        }

        return userService.findById(id).map(user -> {
            user.setEmail(newUser.getEmail());
            user.setUsername(newUser.getUsername());
            user.setName(newUser.getName());
            user.setPassword(newUser.getPassword());
            return userService.save(user);
        }).orElseThrow(() -> {
            throw new UserNotFoundException(id);
        });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    void delete(@PathVariable Long id) {
        userService.findById(id).ifPresentOrElse(user -> {
            user.setActive(false);
            userService.save(user);
        }, () -> {
            throw new UserNotFoundException(id);
        });
    }
}
