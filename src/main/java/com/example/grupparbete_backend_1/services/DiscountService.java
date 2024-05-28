package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.models.Discount;

import java.util.List;

public interface DiscountService {
    public Discount calculateDiscountSunToMon(Long bookingId);
    public Discount calculateDiscount2NightsOrMore(Long bookingId);
    List<Discount> getAllDiscountsForBooking(Long bookingId);
    public Double getTotalDiscountValueForBooking(Long bookingId);

}
