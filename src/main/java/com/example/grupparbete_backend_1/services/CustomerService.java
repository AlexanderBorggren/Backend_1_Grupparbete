package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;

import java.util.List;

public interface CustomerService {

    public CustomerDto customerToCustomerDto(Customer kund);

    public DetailedCustomerDto customerToDetailedCustomerDto(Customer kund);

    public Customer detailedCustomerDtoToCustomer(DetailedCustomerDto customer);
    public List<DetailedCustomerDto> getAllCustomer();

    public String addCustomer(DetailedCustomerDto customer);

}
