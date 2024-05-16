package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.Events.EventBase;
import com.example.grupparbete_backend_1.dto.EventDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public interface EventService {
    public void addEventDto(EventDto event);
    public void addEvent(EventBase event);
    public EventDto eventToEventDto(EventBase event);
    public EventBase eventDtoToEvent(EventDto event);
    public List<EventDto> getAllEvent();

}
