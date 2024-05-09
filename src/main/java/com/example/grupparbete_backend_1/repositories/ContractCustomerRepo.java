package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.ContractCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {

   public Optional<ContractCustomer> findById(Long id);
}
