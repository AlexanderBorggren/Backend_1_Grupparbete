package com.example.grupparbete_backend_1.serviceTests;

import com.example.grupparbete_backend_1.configuration.IntegrationProperties;
import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.services.BlacklistService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.impl.BlacklistServiceImpl;
import com.example.grupparbete_backend_1.services.impl.CustomerServiceImpl;
import com.example.grupparbete_backend_1.services.impl.HttpClientProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BlacklistServiceTests {

    @Mock
    private HttpClientProvider httpClientProvider;

    @Mock
    CustomerService customerService;

    private BlacklistService blacklistService;

    @BeforeEach
    void setUp() {

        blacklistService = new BlacklistServiceImpl();
    }

    @Test
    @DisplayName("should return a list")
    void testGetBlacklistedCustomers() throws URISyntaxException, IOException, InterruptedException {

        String jsonResponse = "[\n" +
                "  {\n" +
                "    \"id\": 26,\n" +
                "    \"email\": \"stefan6@aaa.com\",\n" +
                "    \"name\": \"Stefan Holmberg\",\n" +
                "    \"group\": \"rosa\",\n" +
                "    \"created\": \"2024-05-08T15:08:48.636289\",\n" +
                "    \"ok\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 37,\n" +
                "    \"email\": \"karl.nilsson@example.com\",\n" +
                "    \"name\": \"Stefan Holmberg\",\n" +
                "    \"group\": \"rosa\",\n" +
                "    \"created\": \"2024-05-09T11:05:22.185159\",\n" +
                "    \"ok\": false\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 74,\n" +
                "    \"email\": \"test.email@example.com\",\n" +
                "    \"name\": \"Test Name\",\n" +
                "    \"group\": \"rosa\",\n" +
                "    \"created\": \"2024-05-09T22:30:08.310806\",\n" +
                "    \"ok\": true\n" +
                "  }\n" +
                "]";




        ObjectMapper mapper = new ObjectMapper();
        List<BlacklistedCustomerDto> result = mapper.readValue(jsonResponse, new TypeReference<>() {});


        assertEquals(3, result.size());
        assertEquals("Stefan Holmberg", result.get(0).getName());
        assertEquals("karl.nilsson@example.com", result.get(1).getEmail());

    }

    @Test
    void addCustomerFromList() throws JsonProcessingException {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("testis");
        customer.setEmail("test@test.se");

        BlacklistedCustomerDto testUser = new BlacklistedCustomerDto();

        testUser.setId(customer.getId());
        testUser.setEmail(customer.getEmail());
        testUser.setName(customer.getName());
        testUser.setGroup("testgroup");
        testUser.setCreated(Instant.now().toString());
        testUser.setOk(false);
        testUser.setBlacklistMessage("testuser added to blacklist");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInputString = objectMapper.writeValueAsString(testUser);

        assertEquals(1L, testUser.getId());
        assertEquals("test@test.se", testUser.getEmail());
        assertEquals("testis", testUser.getName());
        assertEquals("testgroup", testUser.getGroup());
        assertFalse(testUser.getOk());
        assertEquals("testuser added to blacklist", testUser.getBlacklistMessage());


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
