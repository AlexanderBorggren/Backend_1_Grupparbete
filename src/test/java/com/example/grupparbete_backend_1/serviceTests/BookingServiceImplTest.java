package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.*;
import com.example.grupparbete_backend_1.services.DiscountService;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import com.example.grupparbete_backend_1.services.impl.BlacklistServiceImpl;
import com.example.grupparbete_backend_1.services.impl.BookingServiceImpl;
import com.example.grupparbete_backend_1.services.impl.DiscountServiceImpl;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    @Mock
    private DiscountRepo discountRepo;
    @InjectMocks
    private DiscountServiceImpl discountService;

    @InjectMocks
    private BookingServiceImpl bookingService;


    RoomType roomType = new RoomType(1L, "Single", 0, 300, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

    Room room = new Room(1L,roomType, Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));

    Customer customer = new Customer(1L, "Bert", "9708045566", "bert@gmail.com",Collections.emptyList(),Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));

    Booking booking = new Booking(1L,LocalDate.parse("2024-06-06"),
            LocalDate.parse("2024-06-08"),2,0,customer,room,600.0);

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
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo, roomTypeRepo, discountService, discountRepo);
    }

    @Test
    void bookingToDetailedBookingDto() {
        DetailedBookingDto actual = bookingService.bookingToDetailedBookingDto(booking);

        assertEquals(detailedBookingDto.getId(), actual.getId());
        assertEquals(detailedBookingDto.getStartDate(), actual.getStartDate());
        assertEquals(detailedBookingDto.getEndDate(), actual.getEndDate());
        assertEquals(detailedBookingDto.getGuestQuantity(), actual.getGuestQuantity());

    }
    @Test
    void detailedBookingDtoToBooking() {
        Booking actual = bookingService.detailedBookingDtoToBooking(detailedBookingDto, customer, room);


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
        BookingDto actual = bookingService.bookingToBookingDto(booking);

        assertEquals(booking.getId(), actual.getId());
        assertEquals(booking.getStartDate(), actual.getStartDate());
        assertEquals(booking.getEndDate(), actual.getEndDate());
    }

    @Test
    void bookingDtoToBooking() {

        Booking actual = bookingService.bookingDtoToBooking(bookingDto, customer, room);

        assertEquals(bookingDto.getId(), actual.getId());
        assertEquals(bookingDto.getStartDate(), actual.getStartDate());
        assertEquals(bookingDto.getEndDate(), actual.getEndDate());
        assertEquals(customer, actual.getCustomer());
        assertEquals(room, actual.getRoom());

    }

    @Test
    public void testGetTotalPriceForBooking() {
        Long bookingId = 1L;
        Double expectedPrice = 200.0;

        when(bookingRepo.getTotalPriceForBooking(bookingId)).thenReturn(expectedPrice);

        Double actualPrice = bookingService.getTotalPriceForBooking(bookingId);

        assertEquals(expectedPrice, actualPrice);
        verify(bookingRepo, times(1)).getTotalPriceForBooking(bookingId);
    }

    @Test
    public void testGetTotalBookedNightsLastYear() {
        Long customerId = 1L;
        LocalDate now = LocalDate.now();
        LocalDate oneYearAgo = now.minusDays(365);

        Booking booking1 = new Booking();
        booking1.setStartDate(now.minusDays(30));
        booking1.setEndDate(now.minusDays(20));

        Booking booking2 = new Booking();
        booking2.setStartDate(now.minusDays(10));
        booking2.setEndDate(now.minusDays(5));

        List<Booking> bookings = Arrays.asList(booking1, booking2);

        when(bookingRepo.findBookingsByCustomerIdAndStartDateAfter(customerId, oneYearAgo)).thenReturn(bookings);

        int totalNights = bookingService.getTotalBookedNightsLastYear(customerId);

        int expectedNights = (int) ChronoUnit.DAYS.between(booking1.getStartDate(), booking1.getEndDate()) +
                (int) ChronoUnit.DAYS.between(booking2.getStartDate(), booking2.getEndDate());

        assertEquals(expectedNights, totalNights);
        verify(bookingRepo, times(1)).findBookingsByCustomerIdAndStartDateAfter(customerId, oneYearAgo);
    }




}