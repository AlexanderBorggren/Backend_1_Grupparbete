package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerRepo customerRepo;

    public CustomerController(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }

    @PostMapping("addCustomer")
    public void addCustomer(@RequestBody Customer customer){
        customerRepo.save(customer);

    }

}
