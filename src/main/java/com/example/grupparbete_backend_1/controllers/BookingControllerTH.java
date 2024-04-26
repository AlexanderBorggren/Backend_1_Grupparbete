package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingControllerTH {

    BookingService bookingService;

    public BookingControllerTH(BookingService bookingService){
        this.bookingService=bookingService;
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
    public String addCustomer(@RequestParam String name, @RequestParam String ssn, @RequestParam String email, Model model) {
        model.addAttribute("name", "name");
        model.addAttribute("ssn", "ssn");
        model.addAttribute("email", "email");
        customerService.addCustomer(new DetailedCustomerDto(email, name, ssn));
        return "redirect:/customer/all";
    }




}
