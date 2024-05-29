package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.FetchContractCustomers;
import com.example.grupparbete_backend_1.dto.ContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.models.AllContractCustomers;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.ContractCustomerService;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {
    private static final Logger log = LoggerFactory.getLogger(ContractCustomerServiceImpl.class);
    ContractCustomerRepo contractCustomerRepo;
    public final XmlStreamProvider xmlStreamProvider;

    @Autowired
    public ContractCustomerServiceImpl(ContractCustomerRepo contractCustomerRepo, XmlStreamProvider xmlStreamProvider){
        this.contractCustomerRepo=contractCustomerRepo;
        this.xmlStreamProvider=xmlStreamProvider;
    }

    public void fetchContractCustomers() throws IOException {

        try {
            final Logger logger = LoggerFactory.getLogger(FetchContractCustomers.class);

            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);
            InputStream stream = xmlStreamProvider.getDataStream();

            AllContractCustomers allContractCustomers = xmlMapper.readValue(stream, AllContractCustomers.class);

            for (ContractCustomer customer : allContractCustomers.getContractCustomers()) {
                logger.info("Postal Code: {}", customer.getPostalCode());
                logger.info("Country: {}", customer.getCountry());
                logger.info("Id: {}", customer.getId());
                logger.info("id: {}", contractCustomerToDetailedContractCustomerDto(customer).getId());
                DetailedContractCustomerDto detailedContractCustomerDto = contractCustomerToDetailedContractCustomerDto(customer);
                logger.info("DetailedContractCustomerDto: {}", detailedContractCustomerDto); // här är kunden rätt
                addContractCustomer(detailedContractCustomerDto); //här är den null
                //System.out.println("Successfully added customer with ID " + getById(detailedContractCustomerDto.getId()));

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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
    public DetailedContractCustomerDto getById(Long id) {

        ContractCustomer contractCustomer = contractCustomerRepo.findById(id).orElse(null);
        if(contractCustomer == null) {
            return null;
        }
        return contractCustomerToDetailedContractCustomerDto(contractCustomer);
    }
}
