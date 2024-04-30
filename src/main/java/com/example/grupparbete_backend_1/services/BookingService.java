package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.BookingDto;
import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    public DetailedBookingDto bookingToDetailedBookingDto(Booking booking);
    public Booking detailedBookingDtoToBooking(DetailedBookingDto booking, Customer customer, Room room);

    public BookingDto bookingToBookingDto(Booking booking);
    public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room);


    public List<DetailedBookingDto> getAllBookings();
    public String addBooking(DetailedBookingDto booking);

    String deleteBooking(Long bookingId);

    public boolean isRoomAvailable(Long bookingId, LocalDate startDate, LocalDate endDate, RoomType roomType);
    public DetailedBookingDto findById(Long id);
    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate, RoomType roomType);
}
