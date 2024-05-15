package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.ContractCustomer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {
   List<ContractCustomer> findAllByCompanyNameContains(String companyName, Sort sort);
   List<ContractCustomer> findAllByContactNameContains(String contactName, Sort sort);
   List<ContractCustomer> findAllByContactTitleContains(String contactTitle, Sort sort);

   List<ContractCustomer> findAll(Sort sort);

   public Optional<ContractCustomer> findById(Long id);
}
