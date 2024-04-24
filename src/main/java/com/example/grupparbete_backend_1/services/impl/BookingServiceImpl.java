package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.BookingDto;
import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.RoomService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository // Error without this (Could not autowire. No beans of 'RoomService' type found.)
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final RoomRepo roomRepo;
    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo, RoomRepo roomRepo){
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    public BookingDto bookingToBookingDto(Booking booking) {
        return BookingDto.builder().id(booking.getId()).guestQuantity(booking.getGuestQuantity()).extraBedsQuantity(booking.getExtraBedsQuantity())
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).customer(new CustomerDto(
                booking.getCustomer().getId(),
                booking.getCustomer().getEmail(),
                booking.getCustomer().getName()))
                .room(new RoomDto(booking.getRoom().getId(),
                        new DetailedRoomTypeDto(booking.getRoom().getRoomType().getId(), booking.getRoom().getRoomType().getRoomSize(), booking.getRoom().getRoomType().getMaxExtraBeds()))).build();
    }

    @Override
    public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room) {
        return Booking.builder().id(booking.getId()).customer(customer).room(room).build();
    }
    @Override
    public String addBooking(BookingDto booking) {
        Customer customer = customerRepo.findById(booking.getCustomer().getId()).get();
        Room room = roomRepo.findById(booking.getRoom().getId()).get();
        bookingRepo.save(bookingDtoToBooking(booking, customer, room));
        return "Booking har sparats";
    }
    @Override
    public List<BookingDto> getAllBookings() {
        return bookingRepo.findAll().stream().map(k -> bookingToBookingDto(k)).toList();
    }
}
