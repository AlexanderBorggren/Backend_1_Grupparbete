package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.Events.*;
import com.example.grupparbete_backend_1.services.EventService;
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

@ComponentScan
public class QueueStreamer implements CommandLineRunner {

    @Autowired
    private EventService eventService;


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

            // Nu kan du använda event-objektet och dess metoder
            if(event instanceof RoomClosed) {
                eventService.addEvent(new RoomClosed())
            } else if(event instanceof RoomCleaningFinished) {
                // Hantera RoomCleaningFinished event
            } else if(event instanceof RoomCleaningStarted) {
                // Hantera RoomCleaningStarted event
            } else if(event instanceof RoomOpened) {
                // Hantera RoomOpened event
            }


        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });


    }

}

