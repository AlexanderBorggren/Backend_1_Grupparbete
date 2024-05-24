package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}