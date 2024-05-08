package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippersRepo extends JpaRepository<Shippers, Long> {
}
