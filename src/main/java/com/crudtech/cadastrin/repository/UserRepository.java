package com.crudtech.cadastrin.repository;

import com.crudtech.cadastrin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
