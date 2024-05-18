package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.ContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.ContractCustomerService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {
    ContractCustomerRepo contractCustomerRepo;


    public ContractCustomerServiceImpl(ContractCustomerRepo contractCustomerRepo){
        this.contractCustomerRepo=contractCustomerRepo;
    }

    public List<DetailedContractCustomerDto> getAllContractCustomers(){
        List<DetailedContractCustomerDto> detailedContractCustomerDtoList = new ArrayList<>();
        for(ContractCustomer contractCustomer: contractCustomerRepo.findAll()){
            detailedContractCustomerDtoList.add(contractCustomerToDetailedContractCustomerDto(contractCustomer));
        }
        return detailedContractCustomerDtoList;
    }


    @Override
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer contractCustomer) {
        return ContractCustomerDto.builder().id(contractCustomer.getId()).companyName(contractCustomer.getCompanyName())
                .contactName(contractCustomer.getContactName()).contactTitle(contractCustomer.getContactTitle()).build();
    }

    @Override
    public DetailedContractCustomerDto contractCustomerToDetailedContractCustomerDto(ContractCustomer contractCustomer) {
        return DetailedContractCustomerDto.builder().id(contractCustomer.getId()).companyName(contractCustomer.getCompanyName())
                .contactName(contractCustomer.getContactName()).contactTitle(contractCustomer.getContactTitle())
                .streetAddress(contractCustomer.getStreetAddress()).city(contractCustomer.getCity()).postalCode(contractCustomer.getPostalCode())
                .country(contractCustomer.getCountry()).phone(contractCustomer.getPhone()).fax(contractCustomer.getFax())
                .build();
    }

    @Override
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto contractCustomerDto) {
        return ContractCustomer.builder().id(contractCustomerDto.getId()).companyName(contractCustomerDto.getContactName())
                .contactName(contractCustomerDto.getContactName()).contactTitle(contractCustomerDto.getContactTitle()).build();
    }

    @Override
    public ContractCustomer detailedContractCustomerDtoToContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto) {
        return ContractCustomer.builder().id(detailedContractCustomerDto.getId()).companyName(detailedContractCustomerDto.getCompanyName())
                .contactName(detailedContractCustomerDto.getContactName()).contactTitle(detailedContractCustomerDto.getContactTitle())
                .streetAddress(detailedContractCustomerDto.getStreetAddress()).postalCode(detailedContractCustomerDto.getPostalCode()).city(detailedContractCustomerDto.getCity()).country(detailedContractCustomerDto.getCountry())
                .phone(detailedContractCustomerDto.getPhone()).fax(detailedContractCustomerDto.getFax()).build();
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
    public void addContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto) {
        contractCustomerRepo.save(detailedContractCustomerDtoToContractCustomer(detailedContractCustomerDto));
    }


    @Override
    public String deleteContractCustomer(Long id) {
        return null;
    }
/*    public DetailedCustomerDto findById(Long id) {
        Customer c = customerRepo.findById(id).stream().findFirst().orElse(null);
        if(c == null){
            return null;
        }
        return customerToDetailedCustomerDto(c);
    };*/
    @Override
    public DetailedContractCustomerDto getById(Long id) {

        ContractCustomer contractCustomer = contractCustomerRepo.findById(id).orElse(null);
        if(contractCustomer == null) {
            return null;
        }
        return contractCustomerToDetailedContractCustomerDto(contractCustomer);
    }
}
