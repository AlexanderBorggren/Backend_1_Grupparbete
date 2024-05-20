package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.XmlStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ContractCustomerServiceTests {



    private final ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);
    private final XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);



    private ContractCustomerServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new ContractCustomerServiceImpl(contractCustomerRepo,xmlStreamProvider);

    }


    @Test
    void fetchContractCustomerShouldMapCorrectly() throws IOException {

        //Arrange
        when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("ContractCustomers.xml"));

        //Act
        sut.fetchContractCustomers();
        List<DetailedContractCustomerDto> result = sut.getAllContractCustomers();

        System.out.println(result);

        //Assert
        assertEquals(3, result.size());
        assertEquals("Persson Kommanditbolag", result.get(0).getCompanyName() );
        assertEquals("Maria Åslund", result.get(0).getContactName() );
        assertEquals("gardener", result.get(0).getContactTitle() );

    }




   /* @Test
    void testGetAllContractCustomers() {

        //arrange

        ContractCustomer contractCustomer1  = new ContractCustomer();
        ContractCustomer contractCustomer2 = new ContractCustomer();
        List<ContractCustomer> contractCustomers = Arrays.asList(contractCustomer1, contractCustomer2);

        when(contractCustomerRepo.findAll()).thenReturn(contractCustomers);

        List<DetailedContractCustomerDto> actualDtos = sut.getAllContractCustomers();

        assertEquals(contractCustomers.size(), actualDtos.size());
        for (int i = 0; i < contractCustomers.size(); i++) {
            assertEquals(contractCustomers.get(i).getId(), actualDtos.get(i).getId());
            assertEquals(contractCustomers.get(i).getCompanyName(), actualDtos.get(i).getCompanyName());
            assertEquals(contractCustomers.get(i).getContactName(), actualDtos.get(i).getContactName());
            assertEquals(contractCustomers.get(i).getContactTitle(), actualDtos.get(i).getContactTitle());
            assertEquals(contractCustomers.get(i).getStreetAddress(), actualDtos.get(i).getStreetAddress());
            assertEquals(contractCustomers.get(i).getCity(), actualDtos.get(i).getCity());
            assertEquals(contractCustomers.get(i).getPostalCode(), actualDtos.get(i).getPostalCode());
            assertEquals(contractCustomers.get(i).getCountry(), actualDtos.get(i).getCountry());
            assertEquals(contractCustomers.get(i).getPhone(), actualDtos.get(i).getPhone());
            assertEquals(contractCustomers.get(i).getFax(), actualDtos.get(i).getFax());

        }
    }*/








}
