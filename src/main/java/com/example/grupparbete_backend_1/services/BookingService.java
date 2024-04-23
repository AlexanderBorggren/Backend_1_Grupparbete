package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.BookingDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;

import java.util.List;

public interface BookingService {
    public BookingDto bookingToBookingDto(Booking booking);
    public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room);
    public List<BookingDto> getAllBookings();
    public String addBooking(BookingDto booking);
}
