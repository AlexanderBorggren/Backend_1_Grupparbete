package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.securities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {

    User findByName(String name);
}