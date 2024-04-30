package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.services.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private RoomRepo roomRepo;

    @InjectMocks
    private BookingServiceImpl service;

    private Booking booking;
    private DetailedBookingDto detailedBookingDto;

    Booking book = Booking.builder()
            .id(1L)
                .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(2))
            .guestQuantity(2)
                .extraBedsQuantity(1)
                .build();

    BookingDto bookingDto = BookingDto.builder().id(1L).startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(2)).build();

    DetailedBookingDto detailedBooking = DetailedBookingDto.builder()
            .id(1L)
                .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(2))
            .guestQuantity(2)
                .extraBedsQuantity(1)
                .build();



    @BeforeEach
    void setUp() {
        service = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo);

    }

    @Test
    void bookingToDetailedBookingDto() {
        DetailedBookingDto actual = service.bookingToDetailedBookingDto(booking);

        assertEquals(detailedBooking.getId(), actual.getId());
        assertEquals(detailedBooking.getStartDate(), actual.getStartDate());
        assertEquals(detailedBookingDto.getEndDate(), actual.getEndDate());
        assertEquals(detailedBookingDto.getGuestQuantity(), actual.getGuestQuantity());
        assertEquals(detailedBookingDto.getExtraBedsQuantity(), actual.getExtraBedsQuantity());
    }

    @Test
    void detailedBookingDtoToBooking() {
        Customer customer = new Customer();
        Room room = new Room();
        Booking actual = service.detailedBookingDtoToBooking(detailedBookingDto, customer, room);

        assertEquals(booking.getId(), actual.getId());
        assertEquals(booking.getStartDate(), actual.getStartDate());
        assertEquals(booking.getEndDate(), actual.getEndDate());
        assertEquals(booking.getGuestQuantity(), actual.getGuestQuantity());
        assertEquals(booking.getExtraBedsQuantity(), actual.getExtraBedsQuantity());
        assertEquals(customer, actual.getCustomer());
        assertEquals(room, actual.getRoom());
    }

    @Test
    void bookingToBookingDto() {
        BookingDto actual = service.bookingToBookingDto(booking);

        assertEquals(booking.getId(), actual.getId());
        assertEquals(booking.getStartDate(), actual.getStartDate());
        assertEquals(booking.getEndDate(), actual.getEndDate());
    }

    @Test
    void bookingDtoToBooking() {

        Customer customer = new Customer();
        Room room = new Room();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);
        BookingDto bookingDto = new BookingDto(1L, startDate, endDate);

        Booking actual = service.bookingDtoToBooking(bookingDto, customer, room);

        assertEquals(bookingDto.getId(), actual.getId());
       // assertEquals(bookingDto.getStartDate(), actual.getStartDate());
       // assertEquals(bookingDto.getEndDate(), actual.getEndDate());
        assertEquals(customer, actual.getCustomer());
        assertEquals(room, actual.getRoom());

    }

    @Test
    void addBooking() {
        when(customerRepo.findById(any())).thenReturn(Optional.of(new Customer()));
        when(roomRepo.findById(any())).thenReturn(Optional.of(new Room()));

        String result = service.addBooking(detailedBookingDto);

        assertEquals("Booking har sparats", result);
        verify(bookingRepo, times(1)).save(any());
    }

    @Test
    void getAllBookings() {
        when(bookingRepo.findAll()).thenReturn(Collections.singletonList(booking));

        List<DetailedBookingDto> allBookings = service.getAllBookings();

        assertEquals(1, allBookings.size());
        DetailedBookingDto actual = allBookings.get(0);

        assertEquals(booking.getId(), actual.getId());
        assertEquals(booking.getStartDate(), actual.getStartDate());
        assertEquals(booking.getEndDate(), actual.getEndDate());
        assertEquals(booking.getGuestQuantity(), actual.getGuestQuantity());
        assertEquals(booking.getExtraBedsQuantity(), actual.getExtraBedsQuantity());
    }

    @Test
    void deleteBooking() {
        when(bookingRepo.findById(any())).thenReturn(Optional.of(booking));

        String result = service.deleteBooking(1L);

        assertEquals("1 has been removed.", result);
        verify(bookingRepo, times(1)).delete(any());
    }

    @Test
    void isBookingActive() {
        booking.setStartDate(LocalDate.now().minusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(1));

        when(bookingRepo.findById(any())).thenReturn(Optional.of(booking));

        assertTrue(service.isBookingActive(1L));
    }

}
