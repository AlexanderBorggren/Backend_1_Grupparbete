package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.Events.EventBase;
import com.example.grupparbete_backend_1.dto.EventDto;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;


public interface EventService {
    public void addEventDto(EventDto event);

    public void addEvent(EventBase event);

    public EventDto eventToEventDto(EventBase event);

    public EventBase eventDtoToEvent(EventDto event);

    public List<EventDto> getAllEvent();

    public List<EventDto> getEventsByRoomNo(long roomId);

    public void fetchEventsFromQueueStreaming() throws IOException, TimeoutException;
}
