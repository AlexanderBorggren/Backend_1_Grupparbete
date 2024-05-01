package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.services.BookingService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
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
    public DetailedBookingDto bookingToDetailedBookingDto(Booking booking) {
        return DetailedBookingDto.builder().id(booking.getId()).guestQuantity(booking.getGuestQuantity()).extraBedsQuantity(booking.getExtraBedsQuantity())
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).customer(new CustomerDto(
                booking.getCustomer().getId(),
                booking.getCustomer().getEmail(),
                booking.getCustomer().getName()))
                .room(new RoomDto(booking.getRoom().getId(),
                        new DetailedRoomTypeDto(booking.getRoom().getRoomType().getId(), booking.getRoom().getRoomType().getRoomSize(), booking.getRoom().getRoomType().getMaxExtraBeds()))).build();
    }

    @Override
    public Booking detailedBookingDtoToBooking(DetailedBookingDto booking, Customer customer, Room room) {
        return Booking.builder().id(booking.getId())
                .guestQuantity(booking.getGuestQuantity())
                .extraBedsQuantity(booking.getExtraBedsQuantity())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .customer(customer).room(room).build();
    }


    @Override
    public BookingDto bookingToBookingDto(Booking booking) {
        return BookingDto.builder().id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room) {
        System.out.println("BookingDto id: " + booking.getId()); //debugging
        return Booking.builder().id(booking.getId()).customer(customer).room(room).build();
    }

    @Override
    public String addBooking(DetailedBookingDto booking) {
        Customer customer = customerRepo.findById(booking.getCustomer().getId()).get();
        Room room = roomRepo.findById(booking.getRoom().getId()).get();
        bookingRepo.save(detailedBookingDtoToBooking(booking, customer, room));
        return "Booking har sparats";
    }
    @Override
    public List<DetailedBookingDto> getAllBookings() {
        return bookingRepo.findAll().stream().map(k -> bookingToDetailedBookingDto(k)).toList();
    }

    @Override
    public String deleteBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).get();

        bookingRepo.delete(booking);
        return booking.getId() + " has been removed.";
    }
    @Override
    public boolean isBookingActive(Long bookingId) {
        ChronoLocalDate now = ChronoLocalDate.from(LocalDateTime.now());

        return bookingRepo.findById(bookingId).get()
                .getStartDate().isBefore(now) &&
                bookingRepo.findById(bookingId).get()
                        .getEndDate().isAfter(now);
    }
    @Override
    public DetailedBookingDto findById(Long id) {
        Booking c = bookingRepo.findById(id).stream().findFirst().orElse(null);
        if(c == null){
            return null;
        }
        return bookingToDetailedBookingDto(c);
    };
}
