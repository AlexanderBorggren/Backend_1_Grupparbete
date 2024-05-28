package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.services.impl.BlacklistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;

public class BlacklistServiceUnitTests {

    BlacklistServiceImpl ServiceImplTest = mock(BlacklistServiceImpl.class);

    @BeforeEach
    void setUp(){
        ServiceImplTest = new BlacklistServiceImpl();
    }

    @Test
    @DisplayName("should return a list")
    void testGetBlacklistedCustomers() throws URISyntaxException, IOException {

    }


}



/*
@Override
public List<BlacklistedCustomerDto> getBlacklistedCustomers() throws IOException, InterruptedException, URISyntaxException {

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://javabl.systementor.se/api/rosa/blacklist"))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


    if (response.statusCode() == 200) {

        // Parse the JSON response into a List of BlacklistedCustomerDto objects
        ObjectMapper objectMapper = new ObjectMapper();
        List<BlacklistedCustomerDto> blacklistedCustomers = objectMapper.readValue(response.body(), new TypeReference<>() {
        });

        return blacklistedCustomers;

    }
    else {

        System.out.println("Ett fel uppstod: " + response.statusCode());

        return null;
    }

}
*/
