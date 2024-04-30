package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
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

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void addBooking() throws Exception {
        this.mockMvc.perform(post("/booking/addBooking")
                        .param("roomId", "1")
                        .param("customerId", "1")
                        .param("startDate", "2024-01-15")
                        .param("endDate", "2024-01-20")
                        .param("guestQuantity", "2")
                        .param("extraBedsQuantity", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/booking/all"));
    }
    @Test
    public void deleteBooking() throws Exception{
        this.mockMvc.perform(get("/booking/deleteById/1/")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/booking/all"));
    }

}
