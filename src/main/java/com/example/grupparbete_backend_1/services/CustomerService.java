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

    public void addCustomer(DetailedCustomerDto customer);

    public void deleteCustomer(Long id);

    //Andre - Removed this way of working(How sigrun did it in her video) and instead added a bookingService in customerService
    //Seems to be how it normally is done, but cannot have circular dependancy by doing this.
    //Sources :
    // https://stackoverflow.com/questions/3648635/calling-a-spring-service-class-from-another
    // https://stackoverflow.com/questions/51988182/spring-boot-service-class-calling-another-service-class
    //Copied from bookingService
    //public BookingDto bookingToBookingDto(Booking booking);
    //public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room);

    //Andre -
    // These were only used for adding customer,
    // i instead made bookinglist to be automatically empty when adding new customers
    // Because DetailedCustomerDto has no reference to Room it is the only way it can be done
    // since we didnt want to show it in bookings for /Customers
    // Alternative would be to show roomDto in /Customers so we can then create room from that
    //Copied from roomService
    //public Room roomDtoToRoom(RoomDto room, RoomType roomType);
    //public RoomType roomTypeDtoToRoomType(DetailedRoomTypeDto roomType);

}
