package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.*;
import com.example.grupparbete_backend_1.repositories.*;
import com.example.grupparbete_backend_1.services.BlacklistService;
import com.example.grupparbete_backend_1.services.BookingService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final RoomRepo roomRepo;
    private final RoomTypeRepo roomTypeRepo;
    private final DiscountServiceImpl discountService;
    private final DiscountRepo discountRepo;

    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo, RoomRepo roomRepo, RoomTypeRepo roomTypeRepo, DiscountServiceImpl discountService, DiscountRepo discountRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
        this.roomTypeRepo = roomTypeRepo;
        this.discountService = discountService;
        this.discountRepo = discountRepo;

    }

    @Override
    public DetailedBookingDto bookingToDetailedBookingDto(Booking booking) {
        return DetailedBookingDto.builder().id(booking.getId()).guestQuantity(booking.getGuestQuantity()).extraBedsQuantity(booking.getExtraBedsQuantity())
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).customer(new CustomerDto(
                        booking.getCustomer().getId(),
                        booking.getCustomer().getEmail(),
                        booking.getCustomer().getName()))
                .room(new RoomDto(booking.getRoom().getId(),
                        new DetailedRoomTypeDto(booking.getRoom().getRoomType().getId(), booking.getRoom().getRoomType().getRoomSize(), booking.getRoom().getRoomType().getMaxExtraBeds()))).build();
    }

    @Override
    public Booking detailedBookingDtoToBooking(DetailedBookingDto bookingDto, Customer customer, Room room) {
        int maxExtraBeds = room.getRoomType().getMaxExtraBeds();
        int requestedExtraBeds = bookingDto.getExtraBedsQuantity();
        int validExtraBeds = Math.min(requestedExtraBeds, maxExtraBeds);

        return Booking.builder()
                .id(bookingDto.getId())
                .guestQuantity(bookingDto.getGuestQuantity())
                .extraBedsQuantity(validExtraBeds)
                .startDate(bookingDto.getStartDate())
                .endDate(bookingDto.getEndDate())
                .customer(customer)
                .room(room)
                .build();
    }

    @Override
    public BookingDto bookingToBookingDto(Booking booking) {
        return BookingDto.builder().id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public Booking bookingDtoToBooking(BookingDto bookingDto, Customer customer, Room room) {
        return Booking.builder()
                .id(bookingDto.getId())
                .startDate(bookingDto.getStartDate())
                .endDate(bookingDto.getEndDate())
                .customer(customer)
                .room(room)
                .build();
    }


    @Override
    public String addBooking(DetailedBookingDto booking) throws
            IOException, URISyntaxException, InterruptedException {

        Customer customer = customerRepo.findById(booking.getCustomer().getId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        BlacklistService blacklistService = new BlacklistServiceImpl();
        Room room = roomRepo.findById(booking.getRoom().getId()).orElseThrow(() -> new RuntimeException("Room not found"));

        // Check blacklist
        if (!blacklistService.isBlacklistOk(customer.getEmail())) {
            return customer.getEmail() + " is blacklisted and is not allowed to book a room.";
        }

        // Create booking entity
        Booking newBooking = detailedBookingDtoToBooking(booking, customer, room);
        Booking savedBooking = bookingRepo.save(newBooking);

        // Calculate total price without discounts
        Double totalPriceNoDiscount = bookingRepo.getTotalPriceForBooking(savedBooking.getId());
        if (totalPriceNoDiscount == null) {
            totalPriceNoDiscount = 0.0;
        }

        // Calculate discounts
        Discount discountSunToMon = discountService.calculateDiscountSunToMon(savedBooking.getId());
        Discount discount2NightsPlus = discountService.calculateDiscount2NightsOrMore(savedBooking.getId());


        // Save discounts to the database
        discountRepo.save(discountSunToMon);
        discountRepo.save(discount2NightsPlus);

        // Recalculate total discount value after saving discounts
        Double discountValue = discountService.getTotalDiscountValueForBooking(savedBooking.getId());
        if (discountValue == null) {
            discountValue = 0.0;
        }

        int totalBookedNights = getTotalBookedNightsLastYear(customer.getId());
        if (totalBookedNights >= 10) {
            Double additionalDiscount = totalPriceNoDiscount * 0.02;
            discountValue += additionalDiscount;

            Discount discount10Nights = Discount.builder()
                    .discountValue(additionalDiscount)
                    .booking(savedBooking)
                    .build();
            discountRepo.save(discount10Nights);
        }

        // Calculate total price with discounts
        Double totalPriceWithDiscounts = totalPriceNoDiscount - discountValue;
        savedBooking.setTotalPrice(totalPriceWithDiscounts);

        // Save the updated booking with total price
        bookingRepo.save(savedBooking);

        return "You have created a new booking for customer " + customer.getName() + ". Booked a " +
                room.getRoomType().getRoomSize() + " for " + booking.getGuestQuantity() + " guests and " +
                booking.getExtraBedsQuantity() + " extra beds. Date booked is " + booking.getStartDate() +
                " to " + booking.getEndDate() + ". Total price without discounts: " + totalPriceNoDiscount
                + ". Total price with discounts: " + totalPriceWithDiscounts;
    }

    public String updateBooking(DetailedBookingDto booking) throws
            IOException, URISyntaxException, InterruptedException {
        Customer customer = customerRepo.findById(booking.getCustomer().getId()).get();

        Room room = roomRepo.findById(booking.getRoom().getId()).get();
        BlacklistServiceImpl blacklistService = new BlacklistServiceImpl();

        if (blacklistService.isBlacklistOk(customer.getEmail())) {
            bookingRepo.save(detailedBookingDtoToBooking(booking, customer, room));
            return "You have updated an existing booking for customer " + customer.getName() + ". Booked a " + room.getRoomType().getRoomSize() + " for " + booking.getGuestQuantity() + " guests and " + booking.getGuestQuantity() + " extra beds. Date booked is " + booking.getStartDate() + " to " + booking.getEndDate();
        } else return customer.getEmail() + " is blacklisted, cannot be updated";
    }

    @Override
    public List<DetailedBookingDto> getAllBookings() {
        return bookingRepo.findAll().stream().map(k -> bookingToDetailedBookingDto(k)).toList();
    }

    @Override
    public String deleteBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).get();
        List<Discount> discounts = discountRepo.findByBookingId(bookingId);
        discountRepo.deleteAll(discounts);

        bookingRepo.delete(booking);
        return "Booking with id " + booking.getId() + " has been removed.";
    }

    @Override
    public boolean isRoomAvailable(LocalDate startDate, LocalDate endDate, Long roomId) {
        ChronoLocalDate now = ChronoLocalDate.from(LocalDateTime.now());

        List<Booking> allBookingsWithThisRoom = bookingRepo.findAll().stream().filter(booking -> booking.getRoom().getId().equals(roomId)).toList();

        allBookingsWithThisRoom = allBookingsWithThisRoom.stream().filter(booking -> !booking.getEndDate().isBefore(now)).toList();

        for (Booking booking : allBookingsWithThisRoom) {

            if ((booking.getStartDate().isAfter(startDate) && endDate.isAfter(booking.getStartDate())) ||
                    (booking.getEndDate().isAfter(startDate) && endDate.isAfter(booking.getEndDate())) ||
                    ((booking.getStartDate().isBefore(startDate) || booking.getStartDate().isEqual(startDate)) && (endDate.isBefore(booking.getEndDate()) || endDate.isEqual(booking.getEndDate())))
            ) {
                return false;
            }

        }

        return true;
    }

    @Override
    public DetailedBookingDto findById(Long id) {
        Booking c = bookingRepo.findById(id).stream().findFirst().orElse(null);
        if (c == null) {
            return null;
        }
        return bookingToDetailedBookingDto(c);
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate, RoomType roomType) {

        List<Room> roomIsAvailable = roomRepo.findAll().stream().toList();

        //Filter room
        roomIsAvailable = roomIsAvailable.stream().filter(room -> (room.getRoomType().getId().equals(roomType.getId()))).toList();

        roomIsAvailable = roomIsAvailable.stream().filter(room -> isRoomAvailable(startDate, endDate, room.getId())).toList();


        return roomIsAvailable;
    }


    @Override
    public Double getTotalPriceForBooking(Long bookingId) {
        return bookingRepo.getTotalPriceForBooking(bookingId);
    }

    //Method to check condition for additional 2% dicount on booking price
    public int getTotalBookedNightsLastYear(Long customerId) {
        LocalDate oneYearAgo = LocalDate.now().minusDays(365);
        List<Booking> bookings = bookingRepo.findBookingsByCustomerIdAndStartDateAfter(customerId, oneYearAgo);

        int totalNights = 0;
        for (Booking booking : bookings) {
            long nights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
            totalNights += nights;
        }

        return totalNights;
    }

}