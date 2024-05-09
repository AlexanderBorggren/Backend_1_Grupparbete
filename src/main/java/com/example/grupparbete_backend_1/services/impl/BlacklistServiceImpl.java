package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.models.BlacklistCheckResponse;
import com.example.grupparbete_backend_1.services.BlacklistService;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
@Repository

public class BlacklistServiceImpl implements BlacklistService {

   @Override
    public boolean isBlacklistOk(String email) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://javabl.systementor.se/api/rosa/blacklistcheck/" + email))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Begäran var framgångsrik
            Gson gson = new Gson();
            BlacklistCheckResponse blacklistCheckResponse = gson.fromJson(response.body(), BlacklistCheckResponse.class);
            return blacklistCheckResponse.isOk();
        }
		    else {
                // Något gick fel
                System.out.println("Ett fel uppstod: " + response.statusCode());
                return false;
            }

        };
}
