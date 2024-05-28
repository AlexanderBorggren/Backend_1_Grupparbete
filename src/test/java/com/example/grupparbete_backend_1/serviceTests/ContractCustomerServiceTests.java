package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.XmlStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.*;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ContractCustomerServiceTests {


    private final ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);
    private final XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);

    private ContractCustomerServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new ContractCustomerServiceImpl(contractCustomerRepo,xmlStreamProvider);

    }


        @Test
        void  fetchXmlShouldMapCorrectlyAndSave() throws IOException {
            //arrange
            when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("ContractCustomers.xml"));
            when(contractCustomerRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

            // Act
            sut.fetchContractCustomers();


            // Assert
            verify(contractCustomerRepo, times(1)).save(argThat(contractCustomer -> contractCustomer.getId() == 1L
                    && contractCustomer.getCompanyName().equals("Persson Kommanditbolag")
                    && contractCustomer.getContactName().equals("Maria Åslund")
                    && contractCustomer.getContactTitle().equals("gardener")
                    && contractCustomer.getStreetAddress().equals("Anderssons Gata 259")
                    && contractCustomer.getPostalCode().equals("60843")
                    && contractCustomer.getCity().equals("Kramland")
                    && contractCustomer.getCountry().equals("Sverige")
                    && contractCustomer.getPhone().equals("076-340-7143")
                    && contractCustomer.getFax().equals("1500-16026")));

            verify(contractCustomerRepo, times(1)).save(argThat(contractCustomer -> contractCustomer.getId() == 2L));
            verify(contractCustomerRepo, times(1)).save(argThat(contractCustomer -> contractCustomer.getId() == 3L));


        }


}
