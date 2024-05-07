package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.ContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.ContractCustomerService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {
    ContractCustomerRepo contractCustomerRepo;

    public ContractCustomerServiceImpl(ContractCustomerRepo contractCustomerRepo){
        this.contractCustomerRepo=contractCustomerRepo;
    }

    /* private Long id;
    private String companyName;
    private String contactName;
    private String contactTitle;*/
    @Override
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer contractCustomer) {
        return ContractCustomerDto.builder().id(contractCustomer.getId()).companyName(contractCustomer.getContactName())
                .contactName(contractCustomer.getContactName()).contactTitle(contractCustomer.getContactTitle()).build();
    }
/*private Long id;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String streetAddress;
    private String city;
    private String country;
    private String phone;*/
    @Override
    public DetailedContractCustomerDto contractCustomerToDetailedContractCustomerDto(ContractCustomer contractCustomer) {
        return DetailedContractCustomerDto.builder().id(contractCustomer.getId()).companyName(contractCustomer.getContactName())
                .contactName(contractCustomer.getContactName()).contactTitle(contractCustomer.getContactTitle())
                .streetAddress(contractCustomer.getStreetAddress()).city(contractCustomer.getCity()).country(contractCustomer.getCountry())
                .phone(contractCustomer.getPhone()).build();
    }

    @Override
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto contractCustomerDto) {
        return ContractCustomer.builder().id(contractCustomerDto.getId()).companyName(contractCustomerDto.getContactName())
                .contactName(contractCustomerDto.getContactName()).contactTitle(contractCustomerDto.getContactTitle()).build();
    }

    @Override
    public ContractCustomer detailedContractCustomerDtoToContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto) {
        return ContractCustomer.builder().id(detailedContractCustomerDto.getId()).companyName(detailedContractCustomerDto.getContactName())
                .contactName(detailedContractCustomerDto.getContactName()).contactTitle(detailedContractCustomerDto.getContactTitle())
                .streetAddress(detailedContractCustomerDto.getStreetAddress()).city(detailedContractCustomerDto.getCity()).country(detailedContractCustomerDto.getCountry())
                .phone(detailedContractCustomerDto.getPhone()).build();
    }

    @Override
    public ContractCustomerDto detailedContractCustomerDtoToContractCustomerDto(DetailedContractCustomerDto detailedContractCustomerDto) {
        return ContractCustomerDto.builder().id(detailedContractCustomerDto.getId()).companyName(detailedContractCustomerDto.getContactName())
                .contactName(detailedContractCustomerDto.getContactName()).contactTitle(detailedContractCustomerDto.getContactTitle()).build();
    }

    @Override
    public DetailedContractCustomerDto contractCustomerDtoToDetailedContractCustomerDto(ContractCustomerDto contractCustomerDto) {
        return DetailedContractCustomerDto.builder().id(contractCustomerDto.getId()).companyName(contractCustomerDto.getContactName())
                .contactName(contractCustomerDto.getContactName()).contactTitle(contractCustomerDto.getContactTitle()).build();    }

    @Override
    public List<DetailedContractCustomerDto> getAllDetailedContractCustomers() {
        return contractCustomerRepo.findAll()
                .stream()
                .map(this::contractCustomerToDetailedContractCustomerDto)
                .collect(Collectors.toList());
    }


    @Override
    public void addContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto) {
        contractCustomerRepo.save(detailedContractCustomerDtoToContractCustomer(detailedContractCustomerDto));
    }

    @Override
    public String deleteContractCustomer(Long id) {
        return null;
    }

    @Override
    public DetailedCustomerDto findById(Long id) {
        return null;
    }
}
