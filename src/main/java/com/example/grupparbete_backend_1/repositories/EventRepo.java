package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.Events.EventBase;
import com.example.grupparbete_backend_1.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<EventBase, Long> {
}
