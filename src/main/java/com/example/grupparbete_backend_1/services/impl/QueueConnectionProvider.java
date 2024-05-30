package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.configuration.IntegrationProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class QueueConnectionProvider {

    private final IntegrationProperties properties;

    public QueueConnectionProvider(IntegrationProperties properties) {
        this.properties = properties;
    }



    public Channel getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getEventProperties().getHost());
        factory.setUsername(properties.getEventProperties().getUsername());
        factory.setPassword(properties.getEventProperties().getPassword());

        Connection connection = factory.newConnection();

        return connection.createChannel();
    }
}
