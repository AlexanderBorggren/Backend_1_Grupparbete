package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Collections;
import java.util.List;

@Repository // Error without this (Could not autowire. No beans of 'RoomService' type found.)
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private BookingServiceImpl bookingService;

    public CustomerServiceImpl(CustomerRepo customerRepo, BookingServiceImpl bookingService){
        this.customerRepo = customerRepo;
        this.bookingService = bookingService;
    }


    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        return CustomerDto.builder().id(customer.getId()).name(customer.getName()).build();
    }

    @Override
    public DetailedCustomerDto customerToDetailedCustomerDto(Customer customer) {
        return DetailedCustomerDto.builder().id(customer.getId()).ssn(customer.getSsn()).name(customer.getName()).email(customer.getEmail())
                .bookingDtoList(customer.getBookingList().stream().map(booking -> bookingService.bookingToBookingDto(booking)).toList())
                .build();
    }

    @Override
    public Customer detailedCustomerDtoToCustomer(DetailedCustomerDto customer) {
        return Customer.builder().id(customer.getId()).ssn(customer.getSsn()).name(customer.getName()).email(customer.getEmail())
                .bookingList(Collections.emptyList()).build();
    }

    @Override
    public List<DetailedCustomerDto> getAllCustomer() {
        return customerRepo.findAll().stream().map(k -> customerToDetailedCustomerDto(k)).toList();
    }

    @Override
    public void addCustomer(DetailedCustomerDto customer) {
        customerRepo.save(detailedCustomerDtoToCustomer(customer));
    }
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepo.findById(customerId).get();

        // Check if there are any current bookings
        if (!(customer.getBookingList().stream().anyMatch(booking -> booking.getEndDate().isBefore(ChronoLocalDate.from(LocalDateTime.now()))))) {
            throw new IllegalStateException("Cannot delete customer with current bookings");
        }

        customerRepo.delete(customer);
    }
 /*   @Override
    public void deleteCustomer(Long id) {
        bookingService.getAllBookings().forEach(booking -> bookingService.);
        customerRepo.deleteById(id);
        //customerRepo.saveAll(customerRepo.findAll());
    }*/
//    @Override
//    public BookingDto bookingToBookingDto(Booking booking) {
//        return BookingDto.builder().id(booking.getId())
//                .startDate(booking.getStartDate())
//                .endDate(booking.getEndDate())
//                .build();
//    }

//    @Override
//    public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room) {
//        return Booking.builder().id(booking.getId()).customer(customer).room(room).build();
//    }
//    @Override
//    public Room roomDtoToRoom(RoomDto room, RoomType roomType) {
//        return Room.builder().id(room.getId()).roomType(roomType).build();
//    }
//    @Override
//    public RoomType roomTypeDtoToRoomType(DetailedRoomTypeDto roomType) {
//        return RoomType.builder().id(roomType.getId()).roomSize(roomType.getRoomSize()).maxExtraBeds(roomType.getMaxExtraBeds()).build();
//    }
}
