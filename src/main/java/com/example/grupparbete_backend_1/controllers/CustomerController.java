package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("addCustomer")
    public String addCustomer(@RequestBody DetailedCustomerDto customer){
        customerService.addCustomer(customer);
        return "Kund har sparats";
    }
   /* @RequestMapping("deleteCustomer/{id}/delete")
   public String deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }*/

    @RequestMapping("Customers")
        public List<DetailedCustomerDto> getAllCustomers(){return customerService.getAllCustomer();}

    @PutMapping(path="editCustomer")
     public DetailedCustomerDto editCustomer(@RequestBody DetailedCustomerDto customer){

        return customerService.editCustomer(customer.getId(), customer.getName(), customer.getSsn(), customer.getEmail());
    }



}


