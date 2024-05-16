package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookingRepo extends JpaRepository<Booking, Long> {


    @Query("SELECT rt.pricePerNight * DATEDIFF(b.startDate, b.endDate) " +
            "FROM Booking b " +
            "JOIN Room r ON b.room.id = r.id " +
            "JOIN RoomType rt ON r.roomType.id = rt.id " +
            "WHERE b.id = :bookingId")
    Double calculateTotalPriceForBooking(Long bookingId);

    @Query("SELECT rt.pricePerNight * DATEDIFF(b.startDate, b.endDate) " +
            "* (CASE WHEN DATEDIFF(b.endDate, b.startDate) >= 2 THEN 0.995 ELSE 1 END) " + // 0.5% discount for 2 or more nights
            "* (CASE WHEN DAYOFWEEK(b.startDate) = 1 AND DAYOFWEEK(b.endDate) = 2 THEN 0.98 ELSE 1 END) " + // 2% discount for Sunday to Monday
            "FROM Booking b " +
            "JOIN Room r ON b.room.id = r.id " +
            "JOIN RoomType rt ON r.roomType.id = rt.id " +
            "WHERE b.id = :bookingId")
    Double calculateTotalPriceWithDiscounts(Long bookingId);
}
