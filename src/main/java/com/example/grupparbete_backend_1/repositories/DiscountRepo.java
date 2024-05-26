package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountRepo extends JpaRepository<Discount, Long> {
    List<Discount> findByBookingId(Long bookingId);
    @Query("SELECT SUM(d.discountValue) FROM Discount d WHERE d.booking.id = :bookingId")
    Double findTotalDiscountByBookingId(@Param("bookingId") Long bookingId);

}
