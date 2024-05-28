package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.services.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@Controller
@Validated
@RequestMapping("/booking")
public class BookingControllerTH {

    BookingService bookingService;
    RoomService roomService;
    CustomerService customerService;
    RoomTypeService roomTypeService;
    BlacklistService blacklistService;
    DiscountService discountService;
    EmailingService emailingService;

    public BookingControllerTH(BookingService bookingService, RoomService roomService, CustomerService customerService, RoomTypeService roomTypeService, BlacklistService blacklistService, EmailingService emailingService, DiscountService discountService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.customerService = customerService;
        this.roomTypeService = roomTypeService;
        this.blacklistService = blacklistService;
        this.emailingService = emailingService;
        this.discountService = discountService;
    }

    @RequestMapping("/all")
    public String getAll(Model model) {
        List<DetailedBookingDto> b = bookingService.getAllBookings();

        model.addAttribute("allBookings", b);
        model.addAttribute("bookingTitle", "All bookings: ");
        model.addAttribute("bookingId", "Booking id: ");
        model.addAttribute("customerId", "Customer id: ");
        model.addAttribute("customerName", "Customer name: ");
        model.addAttribute("customerEmail", "Customer email: ");

        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");
        model.addAttribute("totalPrice", "Total price: ");
        return "bookings";
    }

    @RequestMapping(path = "/deleteById/{id}/")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = bookingService.deleteBooking(id);
        redirectAttributes.addFlashAttribute("message", message);


        return "redirect:/booking/all";
    }

    @RequestMapping("/editByView/{id}/")
    public String createByForm(@PathVariable Long id, Model model) {
        DetailedBookingDto booking = bookingService.findById(id);

        model.addAttribute("booking", booking);
        return "updateBookingForm";
    }

    @PostMapping("/update")
    public String updateBooking(@Valid Model model, DetailedBookingDto b, CustomerDto customerDto, RedirectAttributes redirectAttributes) throws IOException, URISyntaxException, InterruptedException {

        model.addAttribute("customer", "Customer: ");
        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room size: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");

        b.setCustomer(customerDto);
        String feedbackMessage = bookingService.updateBooking(b);
        redirectAttributes.addFlashAttribute("feedbackMessageCreateBooking", feedbackMessage);

        return "redirect:/booking/all";
    }


    @PostMapping("/addBooking/{roomId}/{customerId}/{startDate}/{endDate}/{guestQuantity}/{extraBedsQuantity}")
    public String addBookingThroughGuide(@Valid @PathVariable @RequestParam Long roomId,
                                         @RequestParam Long customerId,
                                         @RequestParam LocalDate startDate,
                                         @RequestParam LocalDate endDate,
                                         @RequestParam int guestQuantity,
                                         @RequestParam int extraBedsQuantity, RedirectAttributes redirectAttributes) throws IOException, URISyntaxException, InterruptedException {
        CustomerDto customerDto = customerService.detailedCustomerDtoToCustomerDto(customerService.findById(customerId));
        DetailedBookingDto booking = new DetailedBookingDto(startDate, endDate, guestQuantity, extraBedsQuantity, customerDto, roomService.findById(roomId));

        String feedbackMessage = bookingService.addBooking(booking);
        redirectAttributes.addFlashAttribute("feedbackMessageCreateBooking", feedbackMessage);
        return "redirect:/booking/all";
    }

    //CREATE NEW BOOKING - STAGE 1 - EMPTY FIELDS
    @RequestMapping("/bookingByView/{customerId}/")
    public String sendCustomerToSearch(@Valid @PathVariable Long customerId, Model model) {

        List<DetailedRoomTypeDto> roomTypeList = roomTypeService.getAllRoomType();
        model.addAttribute("roomTypes", roomTypeList);
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("maxExtraBeds", "Max extra beds: ");
        model.addAttribute("customerId", customerId);

        return "searchRooms";
    }

    //CREATE NEW BOOKING - STAGE 2 - AUTOFILL FIELDS
    @RequestMapping(value = "/bookingByViewSearchAvailableRooms/{customerId}/")
    public String sendCustomerToSearch(
            @Valid @PathVariable Long customerId,
            @Valid @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("guestQuantity") int guestQuantity,
            @RequestParam("extraBedsQuantity") int extraBedsQuantity,
            @RequestParam("roomTypeId") Long roomTypeId,
            Model model) {

        List<DetailedRoomTypeDto> roomTypeList = roomTypeService.getAllRoomType();
        model.addAttribute("roomTypes", roomTypeList);
        model.addAttribute("roomTypeIdField", roomTypeId);

        //Dates parameter are string
        List<Room> availableRoomList = bookingService.findAvailableRooms(LocalDate.parse(startDate), LocalDate.parse(endDate), roomTypeService.roomTypeDtoToRoomType(roomTypeService.findById(roomTypeId)));
        model.addAttribute("allRooms", availableRoomList);

        //TABLE HEADS
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("maxExtraBeds", "Max extra beds: ");
        model.addAttribute("roomIdField", roomTypeId);
        model.addAttribute("startDateField", startDate);
        model.addAttribute("endDateField", endDate);
        model.addAttribute("guestQuantityField", guestQuantity);
        model.addAttribute("maxExtraBedsField", extraBedsQuantity);

        return "searchRooms";
    }

    //CREATE NEW BOOKING
    @RequestMapping(value = "/addBooking/{startDate}/{endDate}/{guestQuantity}/{extraBedsQuantity}/{customerId}/{roomId}/")
    public String addBooking(@Valid @PathVariable("startDate") String startDate,
                             @PathVariable("endDate") String endDate,
                             @PathVariable("guestQuantity") int guestQuantity,
                             @PathVariable("extraBedsQuantity") int extraBedsQuantity,
                             @PathVariable("customerId") Long customerId,
                             @PathVariable("roomId") Long roomId,
                             Model model,
                             RedirectAttributes redirectAttributes) throws URISyntaxException, IOException, InterruptedException, MessagingException {

            DetailedBookingDto bookingDto = new DetailedBookingDto(LocalDate.parse(startDate), LocalDate.parse(endDate), guestQuantity, extraBedsQuantity, customerService.detailedCustomerDtoToCustomerDto(customerService.findById(customerId)), roomService.findById(roomId));

            DetailedCustomerDto customer = customerService.findById(customerId);
            String roomType = bookingDto.getRoom().getRoomType().getRoomSize();

            MailRequestDto mailRequestDto = new MailRequestDto();
            mailRequestDto.setTemplateName("BookingConfirmation");
            mailRequestDto.setToEmail(customer.getEmail());
            mailRequestDto.setHTML(true);


            emailingService.sendBookingConfirmationEmail(
                    mailRequestDto,
                    customer.getName(),
                    roomId,
                    roomType,
                    startDate,
                    endDate,
                    guestQuantity,
                    extraBedsQuantity);



        String feedbackMessage = bookingService.addBooking(bookingDto);
            redirectAttributes.addFlashAttribute("feedbackMessageCreateBooking", feedbackMessage);



            return "redirect:/booking/all";
        }


        //UPDATE BOOKING - STAGE 1 - AUTOFILL WITH EXISTING BOOKING INFO
        @RequestMapping("/updateBookingByView/{bookingId}/")
        public String sendUpdateBookingToSearchRooms (@Valid @PathVariable Long bookingId, Model model){
            //TODO - HANDLE NULL CUSTOMER

            DetailedBookingDto thisBooking = bookingService.findById(bookingId);


            List<DetailedRoomTypeDto> roomTypeList = roomTypeService.getAllRoomType();

            model.addAttribute("roomTypes", roomTypeList);
            model.addAttribute("roomId", "Room number: ");
            model.addAttribute("roomSize", "Room Size: ");
            model.addAttribute("maxExtraBeds", "Max extra beds: ");
            model.addAttribute("bookingId", bookingId);

            //Fetch information from existing booking
            model.addAttribute("startDateField", thisBooking.getStartDate());
            model.addAttribute("endDateField", thisBooking.getEndDate());
            model.addAttribute("guestQuantityField", thisBooking.getGuestQuantity());
            model.addAttribute("maxExtraBedsField", thisBooking.getExtraBedsQuantity());
            model.addAttribute("roomTypeIdField", thisBooking.getRoom().getRoomType().getId());


            return "searchRoomsUpdateBooking";
        }

        //UPDATE BOOKING - STAGE 2 - AUTOFILL WITH NEWLY CHOSEN FIELD VALUES
        @RequestMapping(value = "/updateBookingSearchAvailableRooms/{bookingId}/")
        public String updateBookingForm (
                @Valid @PathVariable Long bookingId,
                @Valid @RequestParam("startDate") String startDate,
                @RequestParam("endDate") String endDate,
        @RequestParam("guestQuantity") int guestQuantity,
        @RequestParam("extraBedsQuantity") int extraBedsQuantity,
        @RequestParam("roomTypeId") Long roomTypeId,
        Model model){
            //TODO - HANDLE NULL CUSTOMER

            List<DetailedRoomTypeDto> roomTypeList = roomTypeService.getAllRoomType();
            List<Room> availableRoomList = bookingService.findAvailableRooms(LocalDate.parse(startDate), LocalDate.parse(endDate), roomTypeService.roomTypeDtoToRoomType(roomTypeService.findById(roomTypeId)));

            model.addAttribute("roomTypes", roomTypeList);
            model.addAttribute("roomTypeIdField", roomTypeId);
            model.addAttribute("allRooms", availableRoomList);

            //TABLE HEADS
            model.addAttribute("roomId", "Room number: ");
            model.addAttribute("roomSize", "Room Size: ");
            model.addAttribute("maxExtraBeds", "Max extra beds: ");

            //model.addAttribute("roomId", );
            model.addAttribute("roomIdField", roomTypeId);
            model.addAttribute("startDateField", startDate);
            model.addAttribute("endDateField", endDate);
            model.addAttribute("guestQuantityField", guestQuantity);
            model.addAttribute("maxExtraBedsField", extraBedsQuantity);


            return "searchRoomsUpdateBooking";
        }

        //UPDATE BOOKING - LAST STAGE - ACTUALLY UPDATE AN EXISTING BOOKING WITH CHOSEN VALUES
        @RequestMapping(value = "/updateBooking/{startDate}/{endDate}/{guestQuantity}/{extraBedsQuantity}/{bookingId}/{roomId}/")
        public String updateBooking (@Valid @PathVariable("startDate") String startDate,
                @PathVariable("endDate") String endDate,
        @PathVariable("guestQuantity") int guestQuantity,
        @PathVariable("extraBedsQuantity") int extraBedsQuantity,
        @PathVariable("bookingId") Long bookingId,
        @PathVariable("roomId") Long roomId,
        RedirectAttributes redirectAttributes) throws IOException, URISyntaxException, InterruptedException {

            //SET NEW VALUES FOR EXISTING BOOKING
            DetailedBookingDto bookingDto = bookingService.findById(bookingId);
            bookingDto.setStartDate(LocalDate.parse(startDate));
            bookingDto.setEndDate(LocalDate.parse(endDate));
            bookingDto.setGuestQuantity(guestQuantity);
            bookingDto.setExtraBedsQuantity(extraBedsQuantity);
            bookingDto.setRoom(roomService.findById(roomId));



            String feedbackMessage = bookingService.updateBooking(bookingDto);



            redirectAttributes.addFlashAttribute("feedbackMessageUpdateBooking", feedbackMessage);
            return "redirect:/booking/all";
        }
    }


