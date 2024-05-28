package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class QueueStreamer implements CommandLineRunner {

    @Autowired
    private EventService eventService;
    @Override
    public void run(String... args) throws Exception {
        eventService.fetchEventsFromQueueStreaming();
    }

}

