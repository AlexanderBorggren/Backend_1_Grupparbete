package com.example.grupparbete_backend_1.services.impl;


import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Discount;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.DiscountRepo;
import com.example.grupparbete_backend_1.services.DiscountService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Repository
@Service
public class DiscountServiceImpl implements DiscountService {

    BookingRepo bookingRepo;
    DiscountRepo discountRepo;
    public DiscountServiceImpl(BookingRepo bookingRepo,DiscountRepo discountRepo){
        this.bookingRepo=bookingRepo;
        this.discountRepo=discountRepo;
    }
    @Override
    public Discount calculateDiscountSunToMon(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();

        LocalDate current = startDate;
        Double discountValue = 0.0;

        while (!current.isAfter(endDate)) {
            // Check if the current day is Sunday and the next day is Monday
            if (current.getDayOfWeek() == DayOfWeek.SUNDAY &&
                    current.plusDays(1).getDayOfWeek() == DayOfWeek.MONDAY) {
                // Increment the discount value
                discountValue += booking.getRoom().getRoomType().getPricePerNight() * 0.02;
            }
            // Move to the next day
            current = current.plusDays(1);
        }

        Discount discount = new Discount(discountValue, booking);
        return discount;
    }

    @Override
    public Discount calculateDiscount2NightsOrMore(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        long daysBetween = ChronoUnit.DAYS.between(startDate,endDate);
        Double discountValue = 0.0;
        if(daysBetween>=2){
            discountValue = booking.getRoom().getRoomType().getPricePerNight() * daysBetween * 0.005;
        } else {
            discountValue = 0.0;
        }
        Discount discount = new Discount(discountValue,booking);

        return discount;
    }

    public List<Discount> getAllDiscountsForBooking(Long bookingId){
        return discountRepo.findByBookingId(bookingId);
    }

    @Override
    public Double getTotalDiscountValueForBooking(Long bookingId) {
        return discountRepo.findTotalDiscountByBookingId(bookingId);
    }
}
