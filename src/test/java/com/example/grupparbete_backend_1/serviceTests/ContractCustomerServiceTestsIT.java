package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.XmlStreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ContractCustomerServiceTestsIT {

    @Autowired
    XmlStreamProvider xmlStreamProvider;

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    ContractCustomerServiceImpl sut;

    @Test
    void getContractCustomer() throws IOException {

        sut = new ContractCustomerServiceImpl(contractCustomerRepo, xmlStreamProvider);
        Scanner s = new Scanner(sut.xmlStreamProvider.getDataStream()).useDelimiter("\\A");

        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("<id>"));
        assertTrue(result.contains("</id>"));
        assertTrue(result.contains("<companyName>"));
        assertTrue(result.contains("</companyName>"));
        assertTrue(result.contains("<contactName>"));
        assertTrue(result.contains("</contactName>"));
        assertTrue(result.contains("<contactTitle>"));
        assertTrue(result.contains("</contactTitle>"));
        assertTrue(result.contains("<streetAddress>"));
        assertTrue(result.contains("</streetAddress>"));
        assertTrue(result.contains("<city>"));
        assertTrue(result.contains("</city>"));
        assertTrue(result.contains("<postalCode>"));
        assertTrue(result.contains("</postalCode>"));
        assertTrue(result.contains("<country>"));
        assertTrue(result.contains("</country>"));
        assertTrue(result.contains("<phone>"));
        assertTrue(result.contains("</phone>"));
        assertTrue(result.contains("<fax>"));
        assertTrue(result.contains("</fax>"));


    }




}
