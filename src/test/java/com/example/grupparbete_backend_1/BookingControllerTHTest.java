package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.dto.CustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTHTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // used to simulate HTTP requests
    private
    BookingService bookingService;

    //This test simulates a POST request to the /booking/addBooking endpoint with parameters specifying the details of a booking.
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

/**Performs a GET request to the URL /booking/deleteById/1/.
 * It expects that the HTTP response status is a 3xx Redirection (indicating a successful redirect).
 * It expects that the URL to which the request is redirected is /booking/all.
 */
    @Test
    public void deleteBooking() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/booking/deleteById/1/")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/booking/all"));
    }

    /**
     * Performs a POST request to the URL /booking/update with booking information.
     * It expects that the HTTP response status is a 3xx Redirection (indicating a successful redirect).
     * It expects that the URL to which the request is redirected is /booking/all.
     * It verifies that the addBooking method of the BookingService is called with the provided booking information.
     */
    @Test
    public void updateBooking() throws Exception {
        DetailedBookingDto booking = new DetailedBookingDto();
        CustomerDto customerDto = new CustomerDto();

        mockMvc.perform(post("/booking/update")
                        .param("customer", "Customer")
                        .param("startDate", "Start Date")
                        .param("endDate", "End Date")
                        .param("guestQuantity", "Total guests")
                        .param("roomId", "Room number")
                        .param("roomSize", "Room size")
                        .param("extraBedsQuantity", "Extra beds"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/booking/all"));

        verify(bookingService).addBooking(booking);
    }

    @Test
    public void createBookingByForm() throws Exception {
        Long roomId = 1L;
        Long customerId = 2L;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        int guestQuantity = 2;
        int extraBedsQuantity = 1;

        mockMvc.perform(post("/booking/addBooking")
                        .param("roomId", String.valueOf(roomId))
                        .param("customerId", String.valueOf(customerId))
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("guestQuantity", String.valueOf(guestQuantity))
                        .param("extraBedsQuantity", String.valueOf(extraBedsQuantity)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/booking/all"));

        verify(bookingService).addBooking(any(DetailedBookingDto.class));
    }
}
