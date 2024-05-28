package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.BookingDto;
import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    public DetailedBookingDto bookingToDetailedBookingDto(Booking booking);
    public Booking detailedBookingDtoToBooking(DetailedBookingDto booking, Customer customer, Room room);

    public BookingDto bookingToBookingDto(Booking booking);
    public Booking bookingDtoToBooking(BookingDto booking, Customer customer, Room room);


    public List<DetailedBookingDto> getAllBookings();
    public String addBooking(DetailedBookingDto booking) throws IOException, URISyntaxException, InterruptedException;
    public String updateBooking(DetailedBookingDto booking) throws IOException, URISyntaxException, InterruptedException;


    String deleteBooking(Long bookingId);

    boolean isRoomAvailable(LocalDate startDate, LocalDate endDate, Long roomId);

    public DetailedBookingDto findById(Long id);
    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate, RoomType roomType);
  //  public Double calculateTotalPriceForBooking(Long id);
   // public Double calculateTotalPriceWithDiscounts(Long roomId, LocalDate startDate, LocalDate endDate);
  public Double getTotalPriceForBooking(Long bookingId);
}
