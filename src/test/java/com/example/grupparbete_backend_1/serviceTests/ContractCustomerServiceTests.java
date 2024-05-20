package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.models.AllContractCustomers;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.XmlStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
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
        when(xmlStreamProvider.getDataStream()).thenReturn(getClass()
                .getClassLoader().getResourceAsStream("ContractCustomers.xml"));

        ContractCustomer customer1 = new ContractCustomer();
        customer1.setId(1L);
        customer1.setCompanyName("Persson Kommanditbolag");
        customer1.setContactName("Maria Åslund");
        customer1.setContactTitle("gardener");
        customer1.setStreetAddress("Anderssons Gata 259");
        customer1.setCity("Kramland");
        customer1.setPostalCode("60843");
        customer1.setCountry("Sverige");
        customer1.setPhone("076-340-7143");
        customer1.setFax("1500-16026");

        ContractCustomer  customer2 = new ContractCustomer ();
        customer2.setId(2L);
        customer2.setCompanyName("Karlsson-Eriksson");
        customer2.setContactName("Jörgen Gustafsson");
        customer2.setContactTitle("philosopher");
        customer2.setStreetAddress("Undre Villagatan 451");
        customer2.setCity("Alingtorp");
        customer2.setPostalCode("28838");
        customer2.setCountry("Sverige");
        customer2.setPhone("070-369-5518");
        customer2.setFax("7805-209976");

        ContractCustomer  customer3 = new ContractCustomer ();
        customer3.setId(3L);
        customer3.setCompanyName("Eriksson Group");
        customer3.setContactName("Anna Karlsson");
        customer3.setContactTitle("journalist");
        customer3.setStreetAddress("Johanssons Väg 036");
        customer3.setCity("Arlöv");
        customer3.setPostalCode("77616");
        customer3.setCountry("Sverige");
        customer3.setPhone("076-904-2433");
        customer3.setFax("8653-585976");



        /*List<ContractCustomer> mockCustomers = Arrays.asList(customer1, customer2, customer3);
        when(contractCustomerRepo.findAll()).thenReturn(mockCustomers);*/

        List<DetailedContractCustomerDto> result = Arrays.asList
                (sut.contractCustomerToDetailedContractCustomerDto(customer1),
                        sut.contractCustomerToDetailedContractCustomerDto(customer2),
                        sut.contractCustomerToDetailedContractCustomerDto(customer3));
        when(sut.getAllContractCustomers()).thenReturn(result);


        //Act
        sut.fetchContractCustomers();


       /* List<DetailedContractCustomerDto> result = sut.getAllContractCustomers();*/

        /*result.add(sut.contractCustomerToDetailedContractCustomerDto(customer2));
        */
        System.out.println(result);

        //Assert
        assertEquals(3, result.size());
        assertEquals("Persson Kommanditbolag", result.get(0).getCompanyName());
        assertEquals("Maria Åslund", result.get(0).getContactName());
        assertEquals("gardener", result.get(0).getContactTitle());
        assertEquals("Arlöv", result.get(2).getCity());

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
