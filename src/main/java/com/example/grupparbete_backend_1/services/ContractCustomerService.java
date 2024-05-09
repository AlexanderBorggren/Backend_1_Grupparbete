package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.ContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {

    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer contractCustomer);

    public DetailedContractCustomerDto contractCustomerToDetailedContractCustomerDto(ContractCustomer contractCustomer);

    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto contractCustomerDto);
    public ContractCustomer detailedContractCustomerDtoToContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto);
    public ContractCustomerDto detailedContractCustomerDtoToContractCustomerDto(DetailedContractCustomerDto detailedContractCustomerDto);
    public DetailedContractCustomerDto contractCustomerDtoToDetailedContractCustomerDto(ContractCustomerDto contractCustomerDto);

    public void addContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto);

    public String deleteContractCustomer(Long id);

    public DetailedContractCustomerDto getById(Long id);
    public List<DetailedContractCustomerDto> getAllContractCustomers();



}
