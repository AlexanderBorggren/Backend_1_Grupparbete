package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.models.AllContractCustomers;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.XmlStreamProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class ContractCustomerServiceTests {



    private final ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);
    private final XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);

    AllContractCustomers allContractCustomers = mock(AllContractCustomers.class);



    private ContractCustomerServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new ContractCustomerServiceImpl(contractCustomerRepo,xmlStreamProvider);


    }

/*
    @Test
    void fetchContractCustomerShouldMapCorrectly() throws IOException {


            InputStream xmlInputStream = getClass().getClassLoader().getResourceAsStream("ContractCustomers.xml");
            byte[] bytes = xmlInputStream.readAllBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            when(xmlStreamProvider.getDataStream()).thenReturn(byteArrayInputStream);

            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);

            AllContractCustomers allContractCustomers = xmlMapper.readValue(byteArrayInputStream, AllContractCustomers.class);

            // Act
            sut.fetchContractCustomers();

            // Assert
            assertEquals(3, allContractCustomers.getContractCustomers().size());
            assertEquals("Persson Kommanditbolag", allContractCustomers.getContractCustomers().get(0).getCompanyName());
            assertEquals("Maria Åslund", allContractCustomers.getContractCustomers().get(0).getContactName());
            assertEquals("gardener", allContractCustomers.getContractCustomers().get(0).getContactTitle());
            assertEquals("Arlöv", allContractCustomers.getContractCustomers().get(2).getCity());

        }*/

        @Test
        void  TestXmlMapperToAllContractCustomers() throws IOException {
            //arrange
            when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("ContractCustomers.xml"));
            when(contractCustomerRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

           /* ContractCustomer contractCustomer = new ContractCustomer();
            contractCustomer.setId(0L);*/



            // Act
            sut.fetchContractCustomers();

            List<DetailedContractCustomerDto> allContractCustomerDtos = sut.getAllContractCustomers();

            // Assert
            verify(contractCustomerRepo, times(1)).save(argThat(contractCustomer -> contractCustomer.getId() == 1L));
            verify(contractCustomerRepo, times(1)).save(argThat(contractCustomer -> contractCustomer.getId() == 2L));
            verify(contractCustomerRepo, times(1)).save(argThat(contractCustomer -> contractCustomer.getId() == 3L));




           /* ));
            List<DetailedContractCustomerDto> actualDtos = sut.getAllContractCustomers();

            assertEquals(3, actualDtos.size());
            assertEquals("Persson Kommanditbolag", actualDtos.get(0).getCompanyName());
            assertEquals("Maria Åslund", actualDtos.get(0).getContactName());
            assertEquals("gardener", actualDtos.get(0).getContactTitle());
            assertEquals("Arlöv", actualDtos.get(2).getCity());*/

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
