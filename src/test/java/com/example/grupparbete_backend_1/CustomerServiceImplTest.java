package com.example.grupparbete_backend_1;

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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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

    Customer customer = Customer.builder().id(customerId).name(name).ssn(ssn).bookingList(Collections.emptyList()).build();
    CustomerDto customerDto = CustomerDto.builder().id(customerId).name(name).email(email).build();
    DetailedCustomerDto detailedCustomerDto = DetailedCustomerDto.builder().id(customerId).name(name).ssn(ssn).bookingDtoList(Collections.emptyList()).build();

    private long customerId2 = 2L;
    private String name2 = "Berra";
    private String email2 = "bloop@gmail.com";
    private String ssn2 = "9409145677";

    Customer customer2 = Customer.builder().id(customerId2).name(name2).ssn(ssn2).bookingList(Collections.emptyList()).build();
    List<DetailedCustomerDto> list = new ArrayList<>();



    @BeforeEach
    void setUp() {
        service = new CustomerServiceImpl(customerRepo, bookingService);
    }

    @Test
    void getAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer, customer2));
        List<DetailedCustomerDto> allCustomers = service.getAllCustomer();
        assertEquals(2, allCustomers.size());
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
    void detailedCustomerDtoToCustomer() {
        Customer actual = service.detailedCustomerDtoToCustomer(detailedCustomerDto);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getSsn(), customer.getSsn());
        assertEquals(actual.getName(), customer.getName());
        assertTrue(actual.getBookingList().isEmpty());

    }

}
