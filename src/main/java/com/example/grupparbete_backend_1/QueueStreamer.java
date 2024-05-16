package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.Events.*;
import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.models.Room;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

@ComponentScan
public class QueueStreamer implements CommandLineRunner {

    @Autowired
    private EventService eventService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomTypeService roomTypeService;


    private String queueName = "06edcc3d-7c5c-4de3-a004-ede368b3a030";


    @Override
    public void run(String... args) throws Exception {
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
            // https://www.baeldung.com/jackson-annotations#bd-jackson-polymorphic-type-handling-annotations


            // Deserialisera meddelandet till rätt EventBase subklass
            EventBase event = mapper.readValue(message, EventBase.class);

            // Använd JSONPath för att extrahera RoomNo
            //String roomNo = JsonPath.read(message, "$.RoomNo");
            JsonNode json = mapper.readTree(message);

            RoomDto room = roomService.findById(json.get("RoomNo").asLong());
            event.setRoomNo(roomService.roomDtoToRoom(  room, roomTypeService.roomTypeDtoToRoomType(roomTypeService.findById(room.getRoomType().getId()))  ));
            event.setTimeStamp(LocalDateTime.parse(json.get("TimeStamp").asText()));


            if(event instanceof RoomClosed) {
                event.setMessage("Room closed ");
            } else if(event instanceof RoomCleaningFinished) {
                event.setMessage("Room cleaning finished ");
            } else if(event instanceof RoomCleaningStarted) {
                event.setMessage("Room cleaning started ");
            } else if(event instanceof RoomOpened) {
                event.setMessage("Room opened ");
            }

            eventService.addEvent(event);

        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });


    }

}

