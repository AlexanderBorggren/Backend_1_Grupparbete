package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.XmlStreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContractCustomerServiceIntegrationTest {

    @Autowired
    XmlStreamProvider xmlStreamProvider;

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    ContractCustomerServiceImpl sut;

    @Test
    void getContractCustomerAndCheckProperties() throws IOException {

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

    @Test
    void getContractCustomerAndSaveToDatabase() throws IOException {

        XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);

        when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("ContractCustomers.xml"));

        sut = new ContractCustomerServiceImpl(contractCustomerRepo, xmlStreamProvider);

        //Arrange
        contractCustomerRepo.deleteAll();


        //Act
        sut.fetchContractCustomers();

        assertEquals(3, contractCustomerRepo.count());



    }




}
