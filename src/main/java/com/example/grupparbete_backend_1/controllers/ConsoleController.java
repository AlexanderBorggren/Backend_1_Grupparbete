package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.dto.UserDto;
import com.example.grupparbete_backend_1.models.PasswordResetToken;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.services.*;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;


@Controller
public class ConsoleController {
    private final ShippersService shippersService;
    private final ContractCustomerService contractCustomerService;
    private final EventService eventService;
    public ConsoleController(ShippersService shippersService, ContractCustomerService contractCustomerService, EventService eventService) {
        this.contractCustomerService = contractCustomerService;
        this.eventService = eventService;
        this.shippersService = shippersService;
    }

    @GetMapping("/console/fetchShippers")
    String fetchShippers(RedirectAttributes redirectAttributes) throws IOException {

        shippersService.fetchShippers();
        String message = "Shippers fetched";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:localhost:8080/consoleapps.html";
    }
    @GetMapping("/console/fetchContractCustomers")
    String fetchContractCustomers(RedirectAttributes redirectAttributes) throws IOException {

        contractCustomerService.fetchContractCustomers();
        String message = "Contract customers fetched";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:localhost:8080/consoleapps.html";
    }
    @GetMapping("/console/fetchEvents")
    String fetchEvents(RedirectAttributes redirectAttributes) throws IOException, TimeoutException {

        eventService.fetchEventsFromQueueStreaming();
        String message = "Message queue stream started";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:localhost:8080/consoleapps.html";
    }




}

