package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final BookingRepo bookingRepo;

    private final CustomerRepo customerRepo;

    BookingController(BookingRepo bookingRepo, CustomerRepo customerRepo){
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
    }

    @PostMapping("addBooking")
    public void addBooking(@RequestBody Booking booking){
        /*Customer customer = customerRepo.findById(1L).get();
        if (customer == null){
            System.out.println("Customer not found");
            return;
        }*/
        bookingRepo.save(booking);
    }




}
