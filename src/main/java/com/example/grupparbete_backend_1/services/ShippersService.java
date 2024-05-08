package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Shippers;

import java.util.List;

public interface ShippersService {

    public ShippersDto shippersToShippersDto(Shippers shippers);
    public Shippers shippersDtoToShippers(ShippersDto shippers);
    public List<ShippersDto> getAllShippers();
    public void addShippers(ShippersDto shippers);


}
