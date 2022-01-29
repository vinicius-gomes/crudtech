package com.crudtech.cadastrin.service.impl;

import com.crudtech.cadastrin.helper.UserHelper;
import com.crudtech.cadastrin.model.User;
import com.crudtech.cadastrin.repository.UserRepository;
import com.crudtech.cadastrin.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserHelper userHelper;

    UserServiceImpl(UserRepository userRepository, UserHelper userHelper) {
        this.userRepository = userRepository;
        this.userHelper = userHelper;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(User newUser) {
        newUser.setActive(true);
        return userRepository.save(newUser);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().filter(User::isActive).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).filter(User::isActive);
    }

}
