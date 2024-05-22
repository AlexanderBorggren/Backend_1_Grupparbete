package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.ShippersService;
import com.example.grupparbete_backend_1.services.impl.JSonStreamProvider;
import com.example.grupparbete_backend_1.services.impl.ShippersServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShippersServiceTests {

    private final ShippersRepo shippersRepo = mock(ShippersRepo.class);
    private final JSonStreamProvider jSonStreamProvider = mock(JSonStreamProvider.class);

    private ShippersServiceImpl sut;

    @BeforeEach
    public void setUp() {
        sut = new ShippersServiceImpl(shippersRepo, jSonStreamProvider);
    }

    @Test
    void fetchShippersShouldMapCorrectly() throws IOException {


        //arrange
        InputStream JsonInputStream = getClass().getClassLoader().getResourceAsStream("Shippers.json");
        byte[] bytes = JsonInputStream.readAllBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        when(jSonStreamProvider.getDataStream()).thenReturn(byteArrayInputStream);

        ObjectMapper objectMapper = new ObjectMapper();


        List<Shippers> theShippers = Arrays.asList(objectMapper.readValue(byteArrayInputStream, Shippers[].class));

        for (Shippers shipper : theShippers) {
            System.out.println(shipper.getExternal_Shippers_Id());
            System.out.println(shipper.getPhone());
            System.out.println(shipper.getCompanyName());

        }

        //act
        sut.fetchShippers();



        // Assert
        assertEquals(3, theShippers.size());
        assertEquals("Svensson-Karlsson", theShippers.get(0).getCompanyName());
        assertEquals("070-569-3764", theShippers.get(0).getPhone());
        assertEquals(1L, theShippers.get(0).getExternal_Shippers_Id());


    }

}
