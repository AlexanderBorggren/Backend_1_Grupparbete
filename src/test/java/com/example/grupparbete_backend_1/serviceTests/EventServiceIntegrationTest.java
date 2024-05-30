package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.Events.*;
import com.example.grupparbete_backend_1.configuration.EventProperties;
import com.example.grupparbete_backend_1.configuration.IntegrationProperties;
import com.example.grupparbete_backend_1.dto.EventDto;
import com.example.grupparbete_backend_1.repositories.EventRepo;
import com.example.grupparbete_backend_1.repositories.UserRepo;
import com.example.grupparbete_backend_1.services.RoomService;
import com.example.grupparbete_backend_1.services.RoomTypeService;
import com.example.grupparbete_backend_1.services.impl.EventServiceImpl;
import com.example.grupparbete_backend_1.services.impl.QueueConnectionProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventServiceIntegrationTest {

    QueueConnectionProvider queueConnectionProvider = mock(QueueConnectionProvider.class);
    RoomTypeService roomTypeService = mock(RoomTypeService.class);
    RoomService roomService = mock(RoomService.class);
    IntegrationProperties properties = mock(IntegrationProperties.class);

    @Autowired
    EventRepo eventRepo;


    Channel mockChannel = mock(Channel.class);
    Delivery delivery = mock(Delivery.class);
    EventProperties eventProperties = mock(EventProperties.class);

    private EventServiceImpl sut;
    @BeforeEach
    void setUp() {
        sut = new EventServiceImpl(eventRepo,roomTypeService,roomService,properties, queueConnectionProvider);
    }

    @Test
    public void getEventAndSaveToDb() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String queueMessage = "{ \"type\": \"RoomClosed\", \"RoomNo\": 123, \"TimeStamp\": \"2024-05-30T11:46:49\" }";
        String queueMessage2 = "{\"type\":\"RoomOpened\",\"RoomNo\":123,\"TimeStamp\":\"2024-05-30T11:46:49\"}";
        String queueMessage3 = "{\"type\":\"RoomCleaningStarted\",\"RoomNo\":123,\"TimeStamp\":\"2024-05-30T11:46:49\"}";
        String queueMessage4 = "{\"type\":\"RoomCleaningFinished\",\"RoomNo\":123,\"TimeStamp\":\"2024-05-30T11:46:49\"}";

        EventBase event = mapper.readValue(queueMessage, EventBase.class);
        EventBase event2 = mapper.readValue(queueMessage2, EventBase.class);
        EventBase event3 = mapper.readValue(queueMessage3, EventBase.class);
        EventBase event4 = mapper.readValue(queueMessage4, EventBase.class);

        eventRepo.deleteAll();

        sut.addEvent(event);
        sut.addEvent(event2);
        sut.addEvent(event3);
        sut.addEvent(event4);

        assertEquals(4, eventRepo.count());
        //assertEquals("123", eventRepo.findAll().get(0).getRoomNo().toString());

    }

}
