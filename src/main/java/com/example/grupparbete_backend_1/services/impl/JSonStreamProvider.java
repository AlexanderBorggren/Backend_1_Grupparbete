package com.example.grupparbete_backend_1.services.impl;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class JSonStreamProvider {

    public InputStream getDataStream() throws IOException {

        URL url = new URL("https://javaintegration.systementor.se/shippers");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Http Error: " + con.getResponseMessage());
        }

        return con.getInputStream();

    }

}
