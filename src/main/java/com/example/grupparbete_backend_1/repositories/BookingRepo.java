package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {


    @Query("SELECT rt.pricePerNight * DATEDIFF(b.endDate, b.startDate) " +
            "FROM Booking b " +
            "JOIN Room r ON b.room.id = r.id " +
            "JOIN RoomType rt ON r.roomType.id = rt.id " +
            "WHERE b.id = :bookingId")
    Double getTotalPriceForBooking(Long bookingId);

    @Query("SELECT b FROM Booking b WHERE b.customer.id = :customerId AND b.startDate >= :startDate")
    List<Booking> findBookingsByCustomerIdAndStartDateAfter(Long customerId, LocalDate startDate);
}

