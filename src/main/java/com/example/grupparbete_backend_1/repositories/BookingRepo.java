package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepo extends JpaRepository<Booking, Long> {


/*    @Query("SELECT rt.pricePerNight * DATEDIFF(b.startDate, b.endDate) " +
            "FROM Booking b " +
            "JOIN Room r ON b.room.id = r.id " +
            "JOIN RoomType rt ON r.roomType.id = rt.id " +
            "WHERE b.id = :bookingId")
    Double calculateTotalPriceForBooking(Long bookingId);*/

    @Query("SELECT " +
            "CASE " +
            "WHEN datediff(:endDate, :startDate) >= 2 THEN rt.pricePerNight * datediff(:endDate, :startDate) * 0.995 " +
            "ELSE rt.pricePerNight * datediff(:endDate, :startDate) " +
            "END " +
            "FROM Room r " +
            "JOIN r.roomType rt " +
            "WHERE r.id = :roomId")
    Double calculateTotalPriceWithDiscounts(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
