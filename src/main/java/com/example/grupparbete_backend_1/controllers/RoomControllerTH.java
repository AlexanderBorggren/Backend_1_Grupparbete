package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.Events.EventBase;
import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.dto.EventDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.EventService;
import com.example.grupparbete_backend_1.services.RoomService;
import com.example.grupparbete_backend_1.services.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Validated
@RequestMapping("/rooms")
public class RoomControllerTH {

     RoomService roomService;
     RoomTypeService roomTypeService;

    public RoomControllerTH(RoomService roomService, RoomTypeService roomTypeService) {
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
    }

    @RequestMapping("/all")
    public String getAll(Model model) {
        List<RoomDto> allRoomsList = roomService.getAllRoom();

        model.addAttribute("allRooms", allRoomsList);
        model.addAttribute("roomTitle", "All Rooms ");
        model.addAttribute("RoomNo", "Room Number ");
        model.addAttribute("RoomType", "Room Type ");
        model.addAttribute("Events", "Events ");

        return "rooms";
    }
}
