package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.services.CustomerService;
import jakarta.transaction.Transactional;
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
    public CustomerDto detailedCustomerDtoToCustomerDto(DetailedCustomerDto customer) {
        return CustomerDto.builder().id(customer.getId()).name(customer.getName()).email(customer.getEmail()).build();
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
    public String deleteCustomer(Long customerId) {
        Customer customer = customerRepo.findById(customerId).get();


        // Check if there are any current bookings
        if ((customer.getBookingList().stream().anyMatch(booking -> booking.getEndDate().isAfter(ChronoLocalDate.from(LocalDateTime.now()))))) {
            return customer.getName() + " has ongoing booking and cannot be deleted.";
        }

        customerRepo.delete(customer);
        return customer.getName() + " has been removed, customer had no active bookings.";
    }

    @Override
    public DetailedCustomerDto editCustomer(Long id, String name, String ssn, String email) {
        Customer c = customerRepo.findById(id).stream().findFirst().orElse(null);
        if(c == null){
            return null;
        }
        c.setName(name);
        c.setSsn(ssn);
        c.setEmail(email);

        customerRepo.save(c);

        return customerToDetailedCustomerDto(c);

    }
    public DetailedCustomerDto findById(Long id) {
        Customer c = customerRepo.findById(id).stream().findFirst().orElse(null);
        if(c == null){
            return null;
        }
        return customerToDetailedCustomerDto(c);
    };

    public boolean doesSsnExist(String ssn) {
        List<DetailedCustomerDto> customers = getAllCustomer();
        return customers.stream()
                .anyMatch(customer -> customer.getSsn().equals(ssn));
    }
    public DetailedCustomerDto findBySsn(String ssn) {
        DetailedCustomerDto c = customerToDetailedCustomerDto(customerRepo.findAll().stream().filter(customer -> customer.getSsn().equals(ssn)).findFirst().orElse(null));
        return c;

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
