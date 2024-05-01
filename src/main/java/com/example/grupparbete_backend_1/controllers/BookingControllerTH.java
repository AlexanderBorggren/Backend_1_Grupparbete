package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.services.BookingService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.RoomService;
import com.example.grupparbete_backend_1.services.RoomTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingControllerTH {

    BookingService bookingService;
    RoomService roomService;
    CustomerService customerService;
    RoomTypeService roomTypeService;

    public BookingControllerTH(BookingService bookingService, RoomService roomService, CustomerService customerService, RoomTypeService roomTypeService) {
        this.bookingService=bookingService;
        this.roomService=roomService;
        this.customerService=customerService;
        this.roomTypeService=roomTypeService;
    }

    @RequestMapping("/all")
    public String getAll(Model model) {
        List<DetailedBookingDto> b = bookingService.getAllBookings();

        model.addAttribute("allBookings", b);
        model.addAttribute("bookingTitle", "All bookings: ");
        model.addAttribute("customerId", "Customer id: ");
        model.addAttribute("customerName", "Customer name: ");
        model.addAttribute("customerEmail", "Customer email: ");

        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");
        return "bookings";
    }

    @RequestMapping(path = "/deleteById/{id}/")
    public String deleteBooking(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        String message = bookingService.deleteBooking(id);
        redirectAttributes.addFlashAttribute("message", message);


        return "redirect:/booking/all";
    }

    @RequestMapping("/editByView/{id}/")
    public String createByForm(@PathVariable Long id, Model model) {
        //System.out.println("hej");
        DetailedBookingDto booking = bookingService.findById(id);

        //TODO - HANDLE NULL CUSTOMER
        model.addAttribute("booking", booking);
        return "updateBookingForm";
    }

    @PostMapping("/update")
    public String updateBooking(Model model, DetailedBookingDto b, CustomerDto customerDto) {

        model.addAttribute("customer", "Customer: ");
        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room size: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");

        b.setCustomer(customerDto);
        bookingService.addBooking(b);

        return "redirect:/booking/all";
    }
    @RequestMapping("/addBookingView")
    public String createBookingByForm(Model model) {
        return "addBookingForm";
    }
    @PostMapping("/addBooking")
    public String addBooking(@RequestParam Long roomId, @RequestParam Long customerId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int guestQuantity, @RequestParam int extraBedsQuantity, Model model) {
        model.addAttribute("customerId", "Customer Id: ");
        model.addAttribute("startDate", "Start Date: ");
        model.addAttribute("endDate", "End Date: ");
        model.addAttribute("guestQuantity", "Total guests: ");
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("extraBedsQuantity", "Extra beds: ");

        DetailedCustomerDto detailedCustomerDto = customerService.findById(customerId);
        bookingService.addBooking(new DetailedBookingDto(startDate, endDate, guestQuantity, extraBedsQuantity, detailedCustomerDto,roomService.findById(roomId)));
        CustomerDto customerDto = customerService.detailedCustomerDtoToCustomerDto(customerService.findById(customerId));
        DetailedBookingDto booking = new DetailedBookingDto(startDate, endDate, guestQuantity, extraBedsQuantity, customerDto,roomService.findById(roomId));
        bookingService.addBooking(booking);
        return "redirect:/booking/all";
    }

    @PostMapping("/addBooking/{roomId}/{customerId}/{startDate}/{endDate}/{guestQuantity}/{extraBedsQuantity}")
    public String addBookingThroughGuide(@PathVariable@RequestParam Long roomId,
                                         @RequestParam Long customerId,
                                         @RequestParam LocalDate startDate,
                                         @RequestParam LocalDate endDate,
                                         @RequestParam int guestQuantity,
                                         @RequestParam int extraBedsQuantity, Model model) {
        CustomerDto customerDto = customerService.detailedCustomerDtoToCustomerDto(customerService.findById(customerId));
        DetailedBookingDto booking = new DetailedBookingDto(startDate, endDate, guestQuantity, extraBedsQuantity, customerDto,roomService.findById(roomId));
        bookingService.addBooking(booking);
        return "redirect:/booking/all";
    }
    @RequestMapping("/bookingByView/{customerId}/")
    public String sendCustomerToSearch(@PathVariable Long customerId, Model model, RedirectAttributes redirectAttributes) {
        //TODO - HANDLE NULL CUSTOMER



        //List<RoomDto> roomList = roomService.getAllRoom();
        List<DetailedRoomTypeDto> roomTypeList = roomTypeService.getAllRoomType();
        model.addAttribute("roomTypes", roomTypeList);
        //model.addAttribute("allRooms", roomList);
        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("maxExtraBeds", "Max extra beds: ");

        //redirectAttributes.addFlashAttribute();
        return "searchRooms";
    }

    @RequestMapping(value = "/bookingByViewSearchAvailableRooms")
    public String sendCustomerToSearch(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("guestQuantity") int guestQuantity,
            @RequestParam("extraBedsQuantity") int extraBedsQuantity,
            @RequestParam("roomTypeId") Long roomTypeId,
            Model model) {
        //TODO - HANDLE NULL CUSTOMER



        System.out.println("ROOM TYPE ID FROM FORM: " + roomTypeId);
        List<DetailedRoomTypeDto> roomTypeList = roomTypeService.getAllRoomType();
        model.addAttribute("roomTypes", roomTypeList);

        List<Room> availableRoomList = bookingService.findAvailableRooms(LocalDate.parse(startDate), LocalDate.parse(endDate), roomTypeService.roomTypeDtoToRoomType(roomTypeService.findById(roomTypeId)));
        //List<RoomDto> availableRoomList = roomService.getAllRoom();
        System.out.println("ROOM LIST AVAILABLE SIZE: " + availableRoomList.size());
        model.addAttribute("allRooms", availableRoomList);

        model.addAttribute("roomId", "Room number: ");
        model.addAttribute("roomSize", "Room Size: ");
        model.addAttribute("maxExtraBeds", "Max extra beds: ");
        return "searchRooms";
    }
   /* @RequestMapping("/booking/searchAvailableRooms/")
    public String searchAvailableRooms(Model model, @RequestParam Long roomId, @RequestParam int maxExtraBeds) {
        bookingService.findAvailableRooms(
                LocalDate.parse(model.getAttribute("startDate").toString()),
                LocalDate.parse(model.getAttribute("endDate").toString()),
        //List<RoomDto> roomList = roomService.getAllRoom();

*/

}
