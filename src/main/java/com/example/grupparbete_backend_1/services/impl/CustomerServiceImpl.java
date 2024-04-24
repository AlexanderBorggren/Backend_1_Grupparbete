package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.services.CustomerService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository // Error without this (Could not autowire. No beans of 'RoomService' type found.)
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;}


    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        return CustomerDto.builder().id(customer.getId()).name(customer.getName()).build();
    }

    @Override
    public DetailedCustomerDto customerToDetailedCustomerDto(Customer customer) {
        return DetailedCustomerDto.builder().id(customer.getId()).ssn(customer.getSsn()).name(customer.getName()).email(customer.getEmail())
                .bookingDtoList(customer.getBookingList().stream().map(booking -> bookingToBookingDto(booking)).toList())
                .build();
    }

    @Override
    public Customer detailedCustomerDtoToCustomer(DetailedCustomerDto customer) {
        return Customer.builder().id(customer.getId()).ssn(customer.getSsn()).name(customer.getName()).email(customer.getEmail())
                .bookingList(customer.getBookingDtoList().stream().map(booking -> bookingDtoToBooking(
                        booking,
                        detailedCustomerDtoToCustomer(customer),
                        roomDtoToRoom(booking.getRoom(), roomTypeDtoToRoomType(booking.getRoom().getRoomType()))
                )).toList()).build();
    }

    @Override
    public List<DetailedCustomerDto> getAllCustomer() {
        return customerRepo.findAll().stream().map(k -> customerToDetailedCustomerDto(k)).toList();
    }

    @Override
    public String addCustomer(DetailedCustomerDto customer) {
        customerRepo.save(detailedCustomerDtoToCustomer(customer));
        return "Kunden har sparats";
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
    public Room roomDtoToRoom(RoomDto room, RoomType roomType) {
        return Room.builder().id(room.getId()).roomType(roomType).build();
    }
    @Override
    public RoomType roomTypeDtoToRoomType(DetailedRoomTypeDto roomType) {
        return RoomType.builder().id(roomType.getId()).roomSize(roomType.getRoomSize()).maxExtraBeds(roomType.getMaxExtraBeds()).build();
    }
}
