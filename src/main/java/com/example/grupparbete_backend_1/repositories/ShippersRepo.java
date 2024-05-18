package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippersRepo extends JpaRepository<Shippers, Long> {
    @Query("SELECT s FROM Shippers s WHERE s.external_Shippers_Id = ?1")
    Shippers findByExternal_Shippers_Id(Long externalShippersId);

}
