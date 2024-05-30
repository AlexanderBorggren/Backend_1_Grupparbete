package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.Events.*;
import com.example.grupparbete_backend_1.configuration.EventProperties;
import com.example.grupparbete_backend_1.configuration.IntegrationProperties;
import com.example.grupparbete_backend_1.dto.EventDto;
import com.example.grupparbete_backend_1.repositories.EventRepo;
import com.example.grupparbete_backend_1.services.EventService;
import com.example.grupparbete_backend_1.services.RoomService;
import com.example.grupparbete_backend_1.services.RoomTypeService;
import com.example.grupparbete_backend_1.services.impl.ContractCustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.EventServiceImpl;
import com.example.grupparbete_backend_1.services.impl.QueueConnectionProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.*;
import jdk.jfr.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class EventServiceTests {

    QueueConnectionProvider queueConnectionProvider = mock(QueueConnectionProvider.class);
    EventRepo eventRepo = mock(EventRepo.class);
    RoomTypeService roomTypeService = mock(RoomTypeService.class);
    RoomService roomService = mock(RoomService.class);
    IntegrationProperties properties = mock(IntegrationProperties.class);

    @BeforeEach
    void setUp() {
        EventServiceImpl sut = new EventServiceImpl(eventRepo,roomTypeService,roomService,properties, queueConnectionProvider);
    }



    @Test
    public void testEventMapping() throws IOException {
        // Skapa ett ObjectMapper-objekt
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Skapa ett JSON-meddelande som representerar en specifik subklass av EventBase
        String queueMessage = "{ \"type\": \"RoomClosed\", \"RoomNo\": 123, \"TimeStamp\": \"2024-05-30T11:46:49\" }";
        String queueMessage2 = "{\"type\":\"RoomOpened\",\"RoomNo\":123,\"TimeStamp\":\"2024-05-30T11:46:49\"}";
        String queueMessage3 = "{\"type\":\"RoomCleaningStarted\",\"RoomNo\":123,\"TimeStamp\":\"2024-05-30T11:46:49\"}";
        String queueMessage4 = "{\"type\":\"RoomCleaningFinished\",\"RoomNo\":123,\"TimeStamp\":\"2024-05-30T11:46:49\"}";

        // Anropa metoden som gör mappningen
        EventBase event = mapper.readValue(queueMessage, EventBase.class);
        EventBase event2 = mapper.readValue(queueMessage2, EventBase.class);
        EventBase event3 = mapper.readValue(queueMessage3, EventBase.class);
        EventBase event4 = mapper.readValue(queueMessage4, EventBase.class);

        assertTrue(event instanceof RoomClosed);
        assertEquals("RoomClosed", event.getClass().getSimpleName());
        assertTrue(event2 instanceof RoomOpened);
        assertEquals("RoomOpened", event2.getClass().getSimpleName());
        assertTrue(event3 instanceof RoomCleaningStarted);
        assertEquals("RoomCleaningStarted", event3.getClass().getSimpleName());
        assertTrue(event4 instanceof RoomCleaningFinished);
        assertEquals("RoomCleaningFinished", event4.getClass().getSimpleName());

    }
}
