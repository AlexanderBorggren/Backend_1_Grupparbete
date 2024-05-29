package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {

}