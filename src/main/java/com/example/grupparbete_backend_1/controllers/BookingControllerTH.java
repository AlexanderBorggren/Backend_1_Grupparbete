package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.RoomService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
;



import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/booking")
public class BookingControllerTH {

    BookingService bookingService;
    RoomService roomService;
    CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(BookingControllerTH.class);



    public BookingControllerTH(BookingService bookingService, RoomService roomService, CustomerService customerService) {
        this.bookingService=bookingService;
        this.roomService=roomService;
        this.customerService=customerService;
    }

    @RequestMapping("/all")
    public String getAllBookings(Model model) {
        List<DetailedBookingDto> b = bookingService.getAllBookings();
        logger.info("Retrieved {} bookings", b.size());

        model.addAttribute("allBookings", b);
        model.addAttribute("bookingTitle", "All bookings: ");
        model.addAttribute("customerId", "Customer id: ");
        model.addAttribute("customerName", "Customer name: ");
        model.addAttribute("customerEmail", "Customer email: ");

        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");
        return "bookings";
    }

    @RequestMapping(path = "/deleteById/{id}/")
    public String deleteBooking(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        String message = bookingService.deleteBooking(id);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/booking/all";
    }

    @RequestMapping("/editByView/{id}/")
    public String createByForm(@PathVariable Long id, Model model) {
        DetailedBookingDto booking = bookingService.findById(id);

        if (booking == null) {
            logger.warn("Booking details not found for booking ID: {}", id);
        } else {
            logger.info("Successfully retrieved booking details for booking ID: {}", id);
        }

        model.addAttribute("booking", booking);
        return "updateBookingForm";
    }


    @PostMapping("/update")
    public String updateBooking(Model model, DetailedBookingDto b, CustomerDto customerDto) {

        model.addAttribute("customer", "Customer: ");
        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room size: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");

        b.setCustomer(customerDto);
        bookingService.addBooking(b);

        return "redirect:/booking/all";
    }
    @RequestMapping("/addBookingView")
    public String createBookingByForm(Model model) {
        return "addBookingForm";
    }

    @PostMapping("/addBooking")
    public String addBooking(@RequestParam Long roomId, @RequestParam Long customerId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int guestQuantity, @RequestParam int extraBedsQuantity, Model model) {
        model.addAttribute("customerId", "Customer Id: ");
        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");

        CustomerDto customerDto = customerService.detailedCustomerDtoToCustomerDto(customerService.findById(customerId));
        DetailedBookingDto booking = new DetailedBookingDto(startDate, endDate, guestQuantity, extraBedsQuantity, customerDto,roomService.findById(roomId));
        bookingService.addBooking(booking);
        logger.info("Booking added successfully.");
        return "redirect:/booking/all";
    }


}
