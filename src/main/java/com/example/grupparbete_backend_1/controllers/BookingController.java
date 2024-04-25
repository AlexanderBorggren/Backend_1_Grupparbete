package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final CustomerService customerService;


    @PostMapping("addBooking")
    public void addBooking(@RequestBody DetailedBookingDto booking){
        /*Customer customer = customerRepo.findById(1L).get();
        if (customer == null){
            System.out.println("Customer not found");
            return;
        }*/
        bookingService.addBooking(booking);
    }

    @RequestMapping("Bookings")
    public List<DetailedBookingDto> getAllBookings(){
        return bookingService.getAllBookings();
    }

}
