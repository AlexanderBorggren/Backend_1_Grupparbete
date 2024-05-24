package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.impl.JSonStreamProvider;
import com.example.grupparbete_backend_1.services.impl.ShippersServiceImpl;
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
public class ShippersServiceIntegrationTest {

    @Autowired
    JSonStreamProvider jsonStreamProvider;

    @Autowired
    ShippersRepo shippersRepo;

    ShippersServiceImpl sut;

    @Test
    void getShippers() throws IOException {

        sut = new ShippersServiceImpl(shippersRepo, jsonStreamProvider);
        Scanner scanner = new Scanner(sut.jsonStreamProvider.getDataStream()).useDelimiter("\\A");

        String result = scanner.hasNext() ? scanner.next() : "";

        assertTrue(result.contains("\"id\":"));
        assertTrue(result.contains("\"email\":"));
        assertTrue(result.contains("\"companyName\":"));
        assertTrue(result.contains("\"contactName\":"));
        assertTrue(result.contains("\"contactTitle\":"));
        assertTrue(result.contains("\"streetAddress\":"));
        assertTrue(result.contains("\"city\":"));
        assertTrue(result.contains("\"postalCode\":"));
        assertTrue(result.contains("\"country\":"));
        assertTrue(result.contains("\"phone\":"));
        assertTrue(result.contains("\"fax\":"));


    }

    @Test
    void getShippersAndCheckSaveToDatabase() throws IOException {

        JSonStreamProvider jsonStreamProvider = mock(JSonStreamProvider.class);

        when(jsonStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));
        sut = new ShippersServiceImpl(shippersRepo, jsonStreamProvider);

        //Arrange
        shippersRepo.deleteAll();

        //Act
        sut.fetchShippers();

        //Assert
        assertEquals(3, shippersRepo.count());

    }
}
