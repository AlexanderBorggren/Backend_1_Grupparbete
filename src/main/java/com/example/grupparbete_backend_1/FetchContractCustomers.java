package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.models.AllContractCustomers;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.services.ContractCustomerService;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URL;
@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(FetchContractCustomers.class);
    @Autowired
    ContractCustomerService contractCustomerService;
    @Override
    public void run(String... args) throws Exception {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        AllContractCustomers allContractCustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), AllContractCustomers.class);

        for(ContractCustomer customer : allContractCustomers.getContractCustomers()){
            logger.info("Postal Code: {}", customer.getPostalCode());
            DetailedContractCustomerDto detailedContractCustomerDto = contractCustomerService.contractCustomerToDetailedContractCustomerDto(customer);
            contractCustomerService.addContractCustomer(detailedContractCustomerDto);
            System.out.println("Successfully added customer with ID " + contractCustomerService.getById(detailedContractCustomerDto.getId()));

        }

    }
}
