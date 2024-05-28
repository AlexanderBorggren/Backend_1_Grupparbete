package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.models.*;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.DiscountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class DiscountServiceImplTest {


    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private DiscountRepo discountRepo;

    @InjectMocks
    private DiscountServiceImpl discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateDiscountSunToMon() {
        // Mock booking data
        LocalDate startDate = LocalDate.of(2024, 5, 19); // Sunday
        LocalDate endDate = LocalDate.of(2024, 5, 20); // Monday
        RoomType roomType = new RoomType("Single",0, 300.0);
        Room room = new Room(roomType);
        Customer customer = new Customer("Korv","9909091919","korv@gmail.com");
        Booking booking = new Booking(1L,startDate, endDate,1,0,customer, room, 300.0);

        when(bookingRepo.findById(anyLong())).thenReturn(Optional.of(booking));
        Discount discount = discountService.calculateDiscountSunToMon(1L);

        assertEquals(6.0, discount.getDiscountValue()); // 300.0 * 0.02
    }

    @Test
    void calculateDiscount2NightsOrMore() {
        LocalDate startDate = LocalDate.of(2024, 5, 19); // Sunday
        LocalDate endDate = LocalDate.of(2024, 5, 22); // Monday
        RoomType roomType = new RoomType("Single",0, 300.0);
        Room room = new Room(roomType);
        Customer customer = new Customer("Korv","9909091919","korv@gmail.com");
        Booking booking = new Booking(1L,startDate, endDate,1,0,customer, room);

        when(bookingRepo.findById(anyLong())).thenReturn(Optional.of(booking));

        Discount discount = discountService.calculateDiscount2NightsOrMore(1L);

        assertEquals(4.5, discount.getDiscountValue()); // 300.0 * 3 night * 0.005
    }

    @Test
    void getAllDiscountsForBooking() {
        Discount discount = new Discount(10.0, new Booking());
        when(discountRepo.findByBookingId(anyLong())).thenReturn(Collections.singletonList(discount));

        List<Discount> discounts = discountService.getAllDiscountsForBooking(1L);

        assertEquals(1, discounts.size());
        assertEquals(10.0, discounts.get(0).getDiscountValue());
    }

    @Test
    void getTotalDiscountValueForBooking() {
        when(discountRepo.findTotalDiscountByBookingId(anyLong())).thenReturn(15.0);

        Double totalDiscountValue = discountService.getTotalDiscountValueForBooking(1L);

        assertEquals(15.0, totalDiscountValue);
    }
}