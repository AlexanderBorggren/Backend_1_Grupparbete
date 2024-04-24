package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;

import java.util.List;

public interface CustomerService {

    public CustomerDto customerToCustomerDto(Customer kund);

    public DetailedCustomerDto customerToDetailedCustomerDto(Customer kund);

    public Customer detailedCustomerDtoToCustomer(DetailedCustomerDto customer);
    public List<DetailedCustomerDto> getAllCustomer();

    public String addCustomer(DetailedCustomerDto customer);
    public BookingDto bookingToBookingDto(Booking booking);
    public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room);
    public Room roomDtoToRoom(RoomDto room, RoomType roomType);
    public RoomType roomTypeDtoToRoomType(DetailedRoomTypeDto roomType);

}
