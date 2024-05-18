package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.Events.EventBase;
import com.example.grupparbete_backend_1.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<EventBase, Long> {

    @Query("SELECT e FROM EventBase e WHERE e.RoomNo.id = ?1")
    List<EventBase> findAllByRoomNo(long roomId);

}
