package com.example.grupparbete_backend_1.services.impl;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.ScatteringByteChannel;

@Service
public class HttpClientProvider {


    public HttpResponse<String> sendHttpRequest(String uri, String method, String jsonInputString) {
        try {
            HttpResponse<String> response;
             HttpClient client = HttpClient.newHttpClient();
                HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                        .uri(new URI(uri))
                        .header("Content-Type", "application/json");

                if ("POST".equalsIgnoreCase(method)) {
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonInputString));
                } else if ("PUT".equalsIgnoreCase(method)) {
                    requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(jsonInputString));
                }

                HttpRequest request = requestBuilder.build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

