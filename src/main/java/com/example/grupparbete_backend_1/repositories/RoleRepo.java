package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.securities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RoleRepo extends CrudRepository<Role, UUID> {

    Role findByName(String name);
}