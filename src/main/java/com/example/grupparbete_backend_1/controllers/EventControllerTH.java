package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.EventDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.services.EventService;
import com.example.grupparbete_backend_1.services.ShippersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Validated
@RequestMapping("/events")

public class EventControllerTH {

    EventService eventService;

    public EventControllerTH(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/getEventByRoomId/{id}/")
    public String createEventList(@PathVariable Long id, Model model) {
        List<EventDto> eventDtoList = eventService.getEventsByRoomNo(id);

        model.addAttribute("eventList", eventDtoList);

        model.addAttribute("eventTitle", "All Events for Room " + id);

        model.addAttribute("id", "Id ");
        model.addAttribute("event", "Event");
        model.addAttribute("timeStamp", "Time");
        model.addAttribute("user", "User");


        return "eventList";

    }
}
