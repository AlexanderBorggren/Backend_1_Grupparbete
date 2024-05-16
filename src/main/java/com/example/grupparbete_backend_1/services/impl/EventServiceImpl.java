package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.Events.EventBase;
import com.example.grupparbete_backend_1.dto.EventDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.repositories.EventRepo;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.services.EventService;
import com.example.grupparbete_backend_1.services.ShippersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    public EventServiceImpl(EventRepo eventRepo){
        this.eventRepo = eventRepo;
    }

    @Override
    public void addEventDto(EventDto event) {
        eventRepo.save(eventDtoToEvent(event));
    }

    @Override
    public void addEvent(EventBase event) {
        eventRepo.save(event);
    }

    @Override
    public EventDto eventToEventDto(EventBase event) {

        return EventDto.builder()
                .id(event.getId())
                .message(event.getMessage())
                .user(event.getUse())
                .RoomNo(event.getRoomNo())
                .TimeStamp(event.getTimeStamp())
                .build();
    }

    @Override
    public EventBase eventDtoToEvent(EventDto eventDto) {
        return EventBase.builder()
                .id(eventDto.getId())
                .message(eventDto.getMessage())
                .RoomNo(eventDto.getRoomNo())
                .TimeStamp(eventDto.getTimeStamp())
                .build();
    }
    @Override
    public List<EventDto> getAllEvent() {
        return eventRepo.findAll().stream().map(this::eventToEventDto).toList();
    }

    @Override
    public List<EventDto> getEventsByRoomNo(long roomId) {
        System.out.println(eventRepo.findAllByRoomNo(roomId).stream().map(this::eventToEventDto).toList());
        return eventRepo.findAllByRoomNo(roomId).stream().map(this::eventToEventDto).toList();
    }

}
