package com.crudtech.cadastrin.service;

import com.crudtech.cadastrin.exception.UserValidationException;
import com.crudtech.cadastrin.model.User;
import com.crudtech.cadastrin.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@DataJpaTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    User user;
    List<User> users;

    @BeforeEach
    void init() {
        user = new User();
        users = new ArrayList<>();

        user.setActive(true);
        user.setUsername("vini_gomes");
        user.setName("Vinicius");
        user.setPassword("12345678");
        user.setEmail("vin.felipes@gmail.com");
        user.setId(1L);

        users.add(user);
    }

    @Test
    void create() {
        User actualUser = userService.create(user);
        Assertions.assertEquals(user, actualUser);
        Assertions.assertTrue(actualUser.isActive());
    }

    @Test
    void createFails() {
        userRepository.save(user);
        UserValidationException exception = Assertions.assertThrows(UserValidationException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals("Validation error: Username and/or email already taken. Please check your credentials", exception.getMessage());
    }

    @Test
    void findAll() {
        users.clear();
        users.add(userRepository.save(user));
        List<User> actualUsers = userService.findAll();
        Assertions.assertEquals(users, actualUsers);
    }

    @Test
    void findAllFails() {
        user.setActive(false);
        List<User> actualUsers = userService.findAll();
        Assertions.assertEquals(Collections.emptyList(), actualUsers);
    }

}
