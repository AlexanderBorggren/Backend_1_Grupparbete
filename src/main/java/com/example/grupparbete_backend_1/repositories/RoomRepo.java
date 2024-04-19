package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
}
