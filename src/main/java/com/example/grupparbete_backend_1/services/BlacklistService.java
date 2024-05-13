package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface BlacklistService {

    public boolean isBlacklistOk(String email) throws IOException, InterruptedException, URISyntaxException;

    public List<BlacklistedCustomerDto> getBlacklistedCustomers() throws IOException, InterruptedException, URISyntaxException;

    public BlacklistedCustomerDto addCustomer(Long id) throws IOException, InterruptedException, URISyntaxException;

    public BlacklistedCustomerDto updateCustomer(String email) throws IOException, InterruptedException, URISyntaxException;

}
