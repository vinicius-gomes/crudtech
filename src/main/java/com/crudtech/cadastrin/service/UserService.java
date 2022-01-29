package com.crudtech.cadastrin.service;

import com.crudtech.cadastrin.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    User create(User newUser);

    List<User> findAll();

    Optional<User> findById(Long id);
}
