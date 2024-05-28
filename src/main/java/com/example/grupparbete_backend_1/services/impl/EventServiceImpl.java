package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.Events.EventBase;
import com.example.grupparbete_backend_1.dto.EventDto;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.repositories.EventRepo;
import com.example.grupparbete_backend_1.services.EventService;
import com.example.grupparbete_backend_1.services.RoomService;
import com.example.grupparbete_backend_1.services.RoomTypeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    private final RoomTypeService roomTypeService;
    private final RoomService roomService;



    public EventServiceImpl(EventRepo eventRepo, RoomTypeService roomTypeService, RoomService roomService) {
        this.roomTypeService = roomTypeService;
        this.eventRepo = eventRepo;
        this.roomService = roomService;
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
                .user(event.getUser())
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
        return eventRepo.findAllByRoomNo(roomId).stream().map(this::eventToEventDto).toList();
    }

    @Override
    public void fetchEventsFromQueueStreaming() throws IOException, TimeoutException {

        String queueName = "06edcc3d-7c5c-4de3-a004-ede368b3a030";
        //TODO: MOVE INTO .ENV

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");


            // Deserialisera meddelandet till rätt EventBase subklass
            EventBase event = mapper.readValue(message, EventBase.class);
            System.out.println(" [x] Received '" + event + "'");

            JsonNode json = mapper.readTree(message);

            RoomDto room = roomService.findById(json.get("RoomNo").asLong());
            event.setRoomNo(roomService.roomDtoToRoom(room, roomTypeService.roomTypeDtoToRoomType(
                    roomTypeService.findById(room.getRoomType().getId()))));
            event.setTimeStamp(LocalDateTime.parse(json.get("TimeStamp").asText()));
            event.setMessage();

            addEvent(event);

        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }


}
