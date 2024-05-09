
package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class BlacklistController {
//https://javabl.systementor.se/api/rosa/blacklist
    private CustomerService customerService;

    public BlacklistController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("https://javabl.systementor.se/api/rosa/blacklist")
    public ResponseEntity<List<DetailedCustomerDto>> GetAll(){

        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }



}

