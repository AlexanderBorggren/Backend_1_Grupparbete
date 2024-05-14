package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Customer;
import java.util.List;

public interface CustomerService {

    public CustomerDto customerToCustomerDto(Customer kund);

    public DetailedCustomerDto customerToDetailedCustomerDto(Customer kund);

    public Customer detailedCustomerDtoToCustomer(DetailedCustomerDto customer);
    public CustomerDto detailedCustomerDtoToCustomerDto(DetailedCustomerDto customer);
    public List<DetailedCustomerDto> getAllCustomer();

    public void addCustomer(DetailedCustomerDto customer);

    public String deleteCustomer(Long id);

    public DetailedCustomerDto findById(Long id);
    public DetailedCustomerDto findBySsn(String ssn);
    public boolean doesSsnExist(String ssn);
    public boolean doesSsnExistUpdate(String ssn, Long id);
    public DetailedCustomerDto findByEmail(String email);



}
