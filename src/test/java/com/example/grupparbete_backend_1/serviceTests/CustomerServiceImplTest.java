package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.services.impl.BookingServiceImpl;
import com.example.grupparbete_backend_1.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Unit tests for the CustomerServiceImpl class.
 * These tests verify the behavior of the CustomerServiceImpl
 * by testing its methods in isolation. Dependencies such as
 * CustomerRepo and BookingServiceImpl are mocked for testing
 * reliability.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private BookingServiceImpl bookingService;

    @InjectMocks
    private CustomerServiceImpl service;


    private long customerId = 1L;
    private String name = "Allan";
    private String email = "al@gmail.com";
    private String ssn = "9011255437";

    Customer customer = new Customer(customerId,name,ssn,email, Collections.emptyList(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
    CustomerDto customerDto = CustomerDto.builder().id(customerId).name(name).email(email).build();
    DetailedCustomerDto detailedCustomerDto = DetailedCustomerDto.builder().id(customerId).name(name).ssn(ssn).email(email).bookingDtoList(Collections.emptyList()).build();


    @BeforeEach
    void setUp() {
        service = new CustomerServiceImpl(customerRepo, bookingService);
    }

    @Test
    void getAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        List<DetailedCustomerDto> allCustomers = service.getAllCustomer();
        assertEquals(1, allCustomers.size());
    }


    @Test
    void addCustomer() {
        service.addCustomer(detailedCustomerDto);
        verify(customerRepo, times(1)).save(any());
    }

    @Test
    void customerToCustomerDto() {
        CustomerDto actual = service.customerToCustomerDto(customer);

        assertEquals(actual.getId(),customer.getId());
        assertEquals(actual.getName(),customer.getName());
    }

    @Test
    void customerToDetailedCustomerDto() {
        DetailedCustomerDto actual = service.customerToDetailedCustomerDto(customer);

        assertEquals(actual.getId(), detailedCustomerDto.getId());
        assertEquals(actual.getSsn(), detailedCustomerDto.getSsn());
        assertEquals(actual.getName(), detailedCustomerDto.getName());
        assertEquals(actual.getBookingDtoList(), detailedCustomerDto.getBookingDtoList());

        assertTrue(actual.getBookingDtoList().isEmpty());
    }

    @Test
    void detailedCustomerDtoToCustomerDto() {
        CustomerDto actual = service.detailedCustomerDtoToCustomerDto(detailedCustomerDto);

        assertEquals(actual.getId(), customerDto.getId());
        assertEquals(actual.getName(), customerDto.getName());
        assertEquals(actual.getEmail(), customerDto.getEmail());
    }

    @Test
    void detailedCustomerDtoToCustomer() {
        Customer actual = service.detailedCustomerDtoToCustomer(detailedCustomerDto);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getSsn(), customer.getSsn());
        assertEquals(actual.getName(), customer.getName());
        assertTrue(actual.getBookingList().isEmpty());

    }


}
