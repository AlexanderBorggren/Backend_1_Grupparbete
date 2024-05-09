package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final CustomerService customerService;


    @PostMapping("AddBooking")
    public void addBooking(@RequestBody DetailedBookingDto booking) throws IOException, URISyntaxException, InterruptedException {

        bookingService.addBooking(booking);
    }

    @RequestMapping("Bookings")
    public List<DetailedBookingDto> getAllBookings(){
        return bookingService.getAllBookings();
    }

}
