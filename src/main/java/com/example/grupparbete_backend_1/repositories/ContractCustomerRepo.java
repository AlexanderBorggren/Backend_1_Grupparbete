package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.ContractCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {
   Page<ContractCustomer> findAllByCompanyNameContainsOrContactNameContains(String companyName, String contactName, Pageable pageable);

   Page<ContractCustomer> findAll(Pageable page);

   public Optional<ContractCustomer> findById(Long id);
}
