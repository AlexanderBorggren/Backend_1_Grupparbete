package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingControllerTH {

    BookingService bookingService;
    RoomService roomService;
    CustomerService customerService;

    public BookingControllerTH(BookingService bookingService, RoomService roomService, CustomerService customerService) {
        this.bookingService=bookingService;
        this.roomService=roomService;
        this.customerService=customerService;
    }

    @RequestMapping("/all")
    public String getAll(Model model) {
        List<DetailedBookingDto> b = bookingService.getAllBookings();

        model.addAttribute("allBookings", b);
        model.addAttribute("bookingTitle", "All bookings: ");
        model.addAttribute("customer", "Customer: ");
        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("room", "Room number: "); //?
        model.addAttribute("extraBedsQuantity", "Extra beds: ");
        return "bookings";
    }

    @RequestMapping(path = "/deleteById/{id}/")
    public String deleteCustomer(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        String message = bookingService.deleteBooking(id);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/booking/all";
    }

    @RequestMapping("/editByView/{id}/")
    public String createByForm(@PathVariable Long id, Model model) {
        //System.out.println("hej");
        DetailedBookingDto booking = bookingService.findById(id);

        //TODO - HANDLE NULL CUSTOMER


        model.addAttribute("booking", booking);
        return "updateBookingForm";
    }

    @PostMapping("/update")
    public String updateCustomer(Model model, DetailedBookingDto b) {
        bookingService.addBooking(b);

        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("room", "Room number: "); //?
        model.addAttribute("extraBedsQuantity", "Extra beds: ");

        return "redirect:/booking/all";
    }
    @RequestMapping("/addCustomerView")
    public String createCustomerByForm(Model model) {
        return "addCustomerForm";
    }

    @PostMapping("/addCustomer")
    public String addBooking(@RequestParam Long roomId, @RequestParam Long customerId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int guestQuantity, @RequestParam int extraBedsQuantity, Model model) {
        model.addAttribute("roomId", "Room Number: ");
        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");


        bookingService.addBooking(new com.example.grupparbete_backend_1.dto.DetailedBookingDto(startDate, endDate, guestQuantity, extraBedsQuantity, customerService.findById(customerId),roomService.findById(roomId)));
        return "redirect:/customer/all";
    }
    public class DetailedBookingDto {
        private Long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private int guestQuantity;
        private int extraBedsQuantity;
        private CustomerDto customer;
        private RoomDto room;


    }

}
