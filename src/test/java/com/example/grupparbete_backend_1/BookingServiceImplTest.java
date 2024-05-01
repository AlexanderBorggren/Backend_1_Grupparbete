package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import com.example.grupparbete_backend_1.services.impl.BookingServiceImpl;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookingServiceImplTest {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private RoomTypeRepo roomTypeRepo;

    @InjectMocks
    private BookingServiceImpl service;


    RoomType roomType = new RoomType(1L, "Single", 0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

    Room room = new Room(1L,roomType, Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));

    Customer customer = new Customer(1L, "Bert", "9708045566", "bert@gmail.com",Collections.emptyList(),Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));

    Booking booking = new Booking(1L,LocalDate.parse("2024-05-06"),
            LocalDate.parse("2024-05-10"),2,0,customer,room,
            Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

    BookingDto bookingDto = new BookingDto(1L,LocalDate.parse("2024-05-06"),LocalDate.parse("2024-05-10"));


    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder()
            .id(1L)
                .startDate(LocalDate.parse("2024-05-06"))
            .endDate(LocalDate.parse("2024-05-10"))
            .guestQuantity(2)
                .extraBedsQuantity(1)
                .build();



    @BeforeEach
    void setUp() {
        service = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo, roomTypeRepo);
    }

    @Test
    void bookingToDetailedBookingDto() {
        DetailedBookingDto actual = service.bookingToDetailedBookingDto(booking);

        assertEquals(detailedBookingDto.getId(), actual.getId());
        assertEquals(detailedBookingDto.getStartDate(), actual.getStartDate());
        assertEquals(detailedBookingDto.getEndDate(), actual.getEndDate());
        assertEquals(detailedBookingDto.getGuestQuantity(), actual.getGuestQuantity());
        assertEquals(detailedBookingDto.getExtraBedsQuantity(), actual.getExtraBedsQuantity());
    }
    @Test
    void detailedBookingDtoToBooking() {
        Booking actual = service.detailedBookingDtoToBooking(detailedBookingDto, customer, room);


        assertEquals(booking.getId(), actual.getId());
        assertEquals(booking.getStartDate(), actual.getStartDate());
        assertEquals(booking.getEndDate(), actual.getEndDate());
        assertEquals(booking.getGuestQuantity(), actual.getGuestQuantity());

        // Check if the extra beds quantity matches
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

     /*   Customer customer = new Customer();
        Room room = new Room();
        LocalDate startDate = LocalDate.parse("2024-05-08");
        LocalDate endDate = LocalDate.parse("2024-05-12");*/
      //  BookingDto bookingDto = new BookingDto(1L, startDate, endDate);

        Booking actual = service.bookingDtoToBooking(bookingDto, customer, room);

        assertEquals(bookingDto.getId(), actual.getId());
        assertEquals(bookingDto.getStartDate(), actual.getStartDate());
        assertEquals(bookingDto.getEndDate(), actual.getEndDate());
        assertEquals(customer, actual.getCustomer());
        assertEquals(room, actual.getRoom());

    }

    @Test
    void addBooking() {
        when(bookingRepo.save(booking)).thenReturn(booking);
        BookingServiceImpl service2 = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo, roomTypeRepo);

        String feedback = service2.addBooking(detailedBookingDto);
        System.out.println("feedback"+feedback);
        assertTrue(feedback.equalsIgnoreCase("Booking saved"));

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

        assertEquals("Booking with id 1 has been removed", result);
        verify(bookingRepo, times(1)).delete(any());
    }


}
