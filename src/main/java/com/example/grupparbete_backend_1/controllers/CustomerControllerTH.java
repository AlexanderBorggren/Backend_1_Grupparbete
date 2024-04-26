package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.impl.CustomerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerControllerTH{

    CustomerService customerService;
    private CustomerRepo customerRepo;

    public CustomerControllerTH(CustomerService customerService, CustomerRepo customerRepo){
        this.customerService=customerService;
        this.customerRepo=customerRepo;
    }

   /* @PostMapping("submitUpdate")
    public String submitUpdate(@RequestParam String name,
                               @RequestParam String ssn,
                               @RequestParam String email, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("ssn", ssn);
        model.addAttribute("email", email);
        System.out.println(name + ssn + email);
        return "index.html";
    }
   @RequestMapping("/test")
    public String testHTML(Model model){
        return "index.html";
    }*/

    @RequestMapping("/all")
    public String getAll(Model model) {
        List<DetailedCustomerDto> k = customerService.getAllCustomer();
        model.addAttribute("allCustomers", k);
        model.addAttribute("customerTitle", "All customers");
        model.addAttribute("name", "Name");
        model.addAttribute("ssn", "SSN");
        model.addAttribute("email", "Email");
        return "customers";
    }

    @RequestMapping(path = "/deleteById/{id}/")
    public String deleteCap(@PathVariable Long id, Model model) {
        customerService.deleteCustomer(id);
        return getAll(model);
    }



}
