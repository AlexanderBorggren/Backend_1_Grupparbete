package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepo extends JpaRepository<RoomType, Long> {
}
