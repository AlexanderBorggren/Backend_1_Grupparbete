package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.impl.CustomerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerControllerTH{

    CustomerService customerService;

    /*@PostMapping("submitUpdate")
    public String submitUpdate(@RequestParam String name,
                               @RequestParam String ssn,
                               @RequestParam String email, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("ssn", ssn);
        model.addAttribute("email", email);
        System.out.println(name + ssn + email);
        return "index.html";
    }*/

    
}
