package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.ShippersService;
import com.example.grupparbete_backend_1.services.impl.JSonStreamProvider;
import com.example.grupparbete_backend_1.services.impl.ShippersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.List;

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
        when(jSonStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("Shippers.json"));


        sut.fetchShippers();




    }

}
