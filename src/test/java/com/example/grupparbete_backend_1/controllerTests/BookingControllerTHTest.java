package com.example.grupparbete_backend_1.controllerTests;

import ch.qos.logback.core.model.Model;
import com.example.grupparbete_backend_1.controllers.BookingControllerTH;
import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.RoomService;
import com.example.grupparbete_backend_1.services.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;



@AutoConfigureMockMvc
@SpringBootTest
public class BookingControllerTHTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // used to simulate HTTP requests
    private BookingServiceImpl bookingService;

    DetailedRoomTypeDto rt1 = new DetailedRoomTypeDto(1L,"Single", 0);
    DetailedRoomTypeDto rt2 = new DetailedRoomTypeDto(2L,"Double room 1", 1);
    DetailedRoomTypeDto rt3 = new DetailedRoomTypeDto(3L,"Double room 2", 2);

    RoomDto r1 = new RoomDto(1L,rt1);
    RoomDto r2 = new RoomDto(2L,rt2);
    RoomDto r3 = new RoomDto(3L,rt3);

    CustomerDto d1 = new CustomerDto(1L, "apa@apsson.se", "Apa");
    CustomerDto d2 = new CustomerDto(2L, "bob@marley.com", "Bob");
    CustomerDto d3 = new CustomerDto(3L, "coco@hotmail.com", "Cobey");

    DetailedBookingDto b1 = new DetailedBookingDto(LocalDate.parse("2024-06-07"), LocalDate.parse("2024-06-10"),2,0,d1, r1);
    DetailedBookingDto b2 = new DetailedBookingDto(LocalDate.parse("2025-07-08"), LocalDate.parse("2025-07-11"),3,1,d2, r2);
    DetailedBookingDto b3 = new DetailedBookingDto(LocalDate.parse("2026-08-09"), LocalDate.parse("2026-08-12"),4,2,d3, r3);


    @Test
    void getAllBookings() throws Exception {

        DetailedRoomTypeDto rt1 = new DetailedRoomTypeDto(1L,"Single", 0);
        DetailedRoomTypeDto rt2 = new DetailedRoomTypeDto(2L,"Double room 1", 1);
        DetailedRoomTypeDto rt3 = new DetailedRoomTypeDto(3L,"Double room 2", 2);

        RoomDto r1 = new RoomDto(1L,rt1);
        RoomDto r2 = new RoomDto(2L,rt2);
        RoomDto r3 = new RoomDto(3L,rt3);

        CustomerDto d1 = new CustomerDto(1L, "apa@apsson.se", "Apa");
        CustomerDto d2 = new CustomerDto(2L, "bob@marley.com", "Bob");
        CustomerDto d3 = new CustomerDto(3L, "coco@hotmail.com", "Cobey");

        DetailedBookingDto b1 = new DetailedBookingDto(LocalDate.parse("2024-06-07"), LocalDate.parse("2024-06-10"),2,0,d1, r1);
        DetailedBookingDto b2 = new DetailedBookingDto(LocalDate.parse("2025-07-08"), LocalDate.parse("2025-07-11"),3,1,d2, r2);
        DetailedBookingDto b3 = new DetailedBookingDto(LocalDate.parse("2026-08-09"), LocalDate.parse("2026-08-12"),4,2,d3, r3);

        // Mock behavior for the findById and getAllCustomer methods of the CustomerService
        when(bookingService.findById(1L)).thenReturn(b1);
        when(bookingService.findById(2L)).thenReturn(b2);
        when(bookingService.findById(3L)).thenReturn(b3);
        when(bookingService.getAllBookings()).thenReturn(Arrays.asList(b1,b2,b3));

        when(bookingService.getAllBookings()).thenReturn(Arrays.asList(b1, b2, b3));

        mockMvc.perform(MockMvcRequestBuilders.get("/booking/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("allBookings"))
                .andExpect(MockMvcResultMatchers.model().attribute("allBookings", Arrays.asList(b1, b2, b3)))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bookingTitle"))
                .andExpect(MockMvcResultMatchers.model().attribute("bookingTitle", "All bookings: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bookingId"))
                .andExpect(MockMvcResultMatchers.model().attribute("bookingId", "Booking id: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerId"))
                .andExpect(MockMvcResultMatchers.model().attribute("customerId", "Customer id: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerName"))
                .andExpect(MockMvcResultMatchers.model().attribute("customerName", "Customer name: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerEmail"))
                .andExpect(MockMvcResultMatchers.model().attribute("customerEmail", "Customer email: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("startDate"))
                .andExpect(MockMvcResultMatchers.model().attribute("startDate", "Start Date: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("endDate"))
                .andExpect(MockMvcResultMatchers.model().attribute("endDate", "End Date: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("guestQuantity"))
                .andExpect(MockMvcResultMatchers.model().attribute("guestQuantity", "Total guests: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roomId"))
                .andExpect(MockMvcResultMatchers.model().attribute("roomId", "Room number: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roomSize"))
                .andExpect(MockMvcResultMatchers.model().attribute("roomSize", "Room Size: "))
                .andExpect(MockMvcResultMatchers.model().attributeExists("extraBedsQuantity"))
                .andExpect(MockMvcResultMatchers.model().attribute("extraBedsQuantity", "Extra beds: "))
                .andExpect(MockMvcResultMatchers.view().name("bookings"));
    }

/**Performs a GET request to the URL /booking/deleteById/1/.
 * It expects that the HTTP response status is a 3xx Redirection (indicating a successful redirect).
 * It expects that the URL to which the request is redirected is /booking/all.
 */
@Test
void deleteBooking() throws Exception {
    Long id = 1L;
    String message = "Booking deleted successfully";

    when(bookingService.deleteBooking(id)).thenReturn(message);

    mockMvc.perform(MockMvcRequestBuilders.get("/booking/deleteById/" + id + "/"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/booking/all"))
            .andExpect(MockMvcResultMatchers.flash().attribute("message", message));

    verify(bookingService).deleteBooking(id);
}


    @Test
    void updateBooking() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/booking/update")
                        .flashAttr("model", new Model()) // mock model
                        .flashAttr("b", b1)
                        .flashAttr("customerDto", d1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/booking/all"));

    }

}
