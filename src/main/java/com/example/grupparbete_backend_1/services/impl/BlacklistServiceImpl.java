package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.services.BlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;




@Service
public class BlacklistServiceImpl implements BlacklistService {



    HttpClient httpClient;


    public BlacklistServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

  @Override
    public boolean isEmailBlacklisted(String email) throws IOException, InterruptedException {
        return false;
    }



    /*@Override
    public boolean isEmailBlacklisted(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://blacklist/api/" + email))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        BlacklistResponse blacklistResponse = objectMapper.readValue(response.body(), BlacklistResponse.class);

        return blacklistResponse.isOk();
    }
*/


}
