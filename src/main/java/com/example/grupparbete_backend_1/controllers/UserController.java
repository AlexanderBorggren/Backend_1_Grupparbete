package com.example.grupparbete_backend_1.controllers;


import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;


@Controller
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/all")
    public String getAll(Model model) throws IOException, URISyntaxException, InterruptedException {
        List<UserDto> allUsers = userService.getAllUsers();

        model.addAttribute("allUsers", allUsers);



        model.addAttribute("bookingTitle", "All bookings: ");
        model.addAttribute("bookingId", "Booking id: ");
        model.addAttribute("customerId", "Customer id: ");
        model.addAttribute("customerName", "Customer name: ");
        model.addAttribute("customerEmail", "Customer email: ");

        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");
        return "users";
    }

    //CREATE NEW BOOKING
    @RequestMapping(value = "/addBooking")
    public String addUser(@RequestParam("mail") String mail,
                          @RequestParam("group") String group,
                             RedirectAttributes redirectAttributes) throws URISyntaxException, IOException, InterruptedException {

        String feedbackMessage = userService.addUser(mail, group);
        redirectAttributes.addFlashAttribute("feedbackMessageCreateBooking", feedbackMessage);

        return "redirect:/user/all";
    }
}
