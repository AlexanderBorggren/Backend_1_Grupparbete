package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.configuration.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class XmlStreamProvider {
    @Autowired
    private IntegrationProperties properties;

    public InputStream getDataStream() throws IOException {

        URL url = new URL(properties.getXml().getUrl());

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Http Error: " + con.getResponseMessage());
        }

        return con.getInputStream();

    }


}
