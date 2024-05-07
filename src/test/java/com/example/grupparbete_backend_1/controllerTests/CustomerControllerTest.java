package com.example.grupparbete_backend_1.controllerTests;

import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Unit tests for the CustomerController class.
 * These tests use MockMvc framework to isolate
 * and verify the behavior of the CustomerController class.
 * Dependencies on CustomerService are mocked for reliability.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    public void init(){
        DetailedCustomerDto d1 = new DetailedCustomerDto(1L,"Apa", "apa@apsson.se", "9903031455", Collections.emptyList());
        DetailedCustomerDto d2 = new DetailedCustomerDto(2L,"Bob", "bob@marley.com", "7905141338",Collections.emptyList());
        DetailedCustomerDto d3 = new DetailedCustomerDto(3L,"Cobey", "coco@hotmail.com", "6710294854",Collections.emptyList());

        // Mock behavior for the findById and getAllCustomer methods of the CustomerService
        // These mocks return the sample DetailedCustomerDto objects when called with specific IDs
        when(customerService.findById(1L)).thenReturn(d1);
        when(customerService.findById(2L)).thenReturn(d2);
        when(customerService.findById(3L)).thenReturn(d3);
        when(customerService.getAllCustomer()).thenReturn(Arrays.asList(d1,d2,d3));


    }

    @Test
    public void addCustomer() throws Exception {
        this.mockMvc.perform(post("/customer/addCustomer")
                    .param("name", "Daim")
                    .param("ssn", "6907015543")
                    .param("email", "daimtårta@hotmail.com"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/customer/all"));
}

    @Test
    public void deleteCustomers() throws Exception{
        this.mockMvc.perform(get("/customer/deleteById/1/")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/customer/all"));
    }

}
