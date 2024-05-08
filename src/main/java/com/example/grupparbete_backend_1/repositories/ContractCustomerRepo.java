package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.ContractCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ContractCustomerRepo extends CrudRepository<ContractCustomer, Long> {
}
