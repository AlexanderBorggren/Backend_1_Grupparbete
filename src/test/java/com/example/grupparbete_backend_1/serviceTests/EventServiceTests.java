package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.Events.EventBase;
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
import com.rabbitmq.client.*;
import jdk.jfr.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

public class EventServiceTests {

    QueueConnectionProvider queueConnectionProvider = mock(QueueConnectionProvider.class);
    EventRepo eventRepo = mock(EventRepo.class);
    RoomTypeService roomTypeService = mock(RoomTypeService.class);
    RoomService roomService = mock(RoomService.class);
    IntegrationProperties properties = mock(IntegrationProperties.class);


    Channel mockChannel = mock(Channel.class);
    Delivery delivery = mock(Delivery.class);
    EventProperties eventProperties = mock(EventProperties.class);



    private EventServiceImpl sut;

    @BeforeEach
    void setUp() {

        sut = new EventServiceImpl(eventRepo,roomTypeService,roomService,properties, queueConnectionProvider);

    }

    List<DeliverCallback> deliverCallbacks = new ArrayList<>();

    EventDto testEvent = new EventDto();
    // {"type":"RoomClosed","TimeStamp":"2024-05-30T06:23:08.87718559","RoomNo":"5"}



 //when(delivery.getBody()).thenReturn("Ditt meddelande här".getBytes());

    @Test
    public void testFetchEventsFromQueueStreaming() throws IOException, TimeoutException, IOException, TimeoutException {


        System.out.println(eventProperties);
        System.out.println(properties.getEventProperties());

        // Skapa mock objekt
        ConnectionFactory factory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        Channel channel = mock(Channel.class);
        Delivery delivery = mock(Delivery.class);

        // Konfigurera mock objekt att returnera förväntade värden
        when(factory.newConnection()).thenReturn(connection);
        when(connection.createChannel()).thenReturn(channel);
        when(delivery.getBody()).thenReturn("{\"type\":\"RoomClosed\",\"TimeStamp\":\"2024-05-30T06:23:08.87718559\",\"RoomNo\":\"5\"}".getBytes());
        when(properties.getEventProperties()).thenReturn(eventProperties);

        // Skapa en instans av din klass och kalla på metoden
        sut.fetchEventsFromQueueStreaming();

        // Verifiera att metoder har kallats på mock objekten
        verify(channel).basicConsume(anyString(), anyBoolean(), any(DeliverCallback.class), any(ConsumerShutdownSignalCallback.class));
    }
}
