package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}