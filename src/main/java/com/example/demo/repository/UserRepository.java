package com.example.demo.repository;

import com.example.demo.demo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Add this line
    Optional<User> findByEmail(String email);

    // ✅ Also useful if you use username for login
    Optional<User> findByUsername(String username);
}
