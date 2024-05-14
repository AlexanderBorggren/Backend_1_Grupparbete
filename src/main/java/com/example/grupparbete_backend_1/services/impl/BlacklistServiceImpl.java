package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.BlacklistCheckResponse;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.services.BlacklistService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Repository

public class BlacklistServiceImpl implements BlacklistService {


    CustomerService customerService;

    @Autowired
    public BlacklistServiceImpl(CustomerService customerService) {
        this.customerService = customerService;}


    public BlacklistServiceImpl(){}

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

        }

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

    @Override
    public BlacklistedCustomerDto addCustomer(Long id) throws IOException, InterruptedException, URISyntaxException {

        List<BlacklistedCustomerDto> blacklistedCustomers = getBlacklistedCustomers();

        System.out.println("Här är jag nu");
        DetailedCustomerDto customer = customerService.findById(id);
        System.out.println("Hämtade kund med " + customer.getId() + customer.getEmail() + customer.getName());

        Optional<BlacklistedCustomerDto> matchingCustomer = blacklistedCustomers.stream()
                .filter(blacklistedCustomer -> blacklistedCustomer.getEmail().equals(customer.getEmail()))
                .findFirst();

        if((matchingCustomer.isPresent() && matchingCustomer.get().getOk()) || !matchingCustomer.isPresent()) {
            System.out.println("no matching customer");
            BlacklistedCustomerDto user = new BlacklistedCustomerDto();

            user.setId(customer.getId());
            user.setName(customer.getName());
            user.setEmail(customer.getEmail());
            user.setGroup("rosa");
            user.setCreated(Instant.now().toString());
            user.setOk(false);

            HttpClient client = HttpClient.newHttpClient();

            // Convert user object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(user);

            // Create HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://javabl.systementor.se/api/rosa/blacklist")) // Replace with your actual API endpoint
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            // Send HttpRequest and get HttpResponse
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response status code
            if (response.statusCode() == 200) {
                // The user was successfully added to the blacklist
                return user;
            } else {
                // There was an error adding the user to the blacklist
                System.out.println("Error adding user to blacklist: " + response.statusCode());
                return null;
            }

        }

       return null;
    }
    @Override
    public BlacklistedCustomerDto updateCustomer(String email) throws IOException, InterruptedException, URISyntaxException {
        System.out.println("inne i updateCustomer");
        List<BlacklistedCustomerDto> blacklistedCustomers = getBlacklistedCustomers();

        Optional<BlacklistedCustomerDto> optionalCustomer = blacklistedCustomers.stream()
                .filter(blacklistedCustomer -> blacklistedCustomer.getEmail().equals(email))
                .findFirst();

        if (optionalCustomer.isPresent()) {
            BlacklistedCustomerDto updateCustomer = optionalCustomer.get();
            System.out.println("hittade " + updateCustomer.getId() + updateCustomer.getEmail() + updateCustomer.getName());

            // Kontrollera om kundens ok-status redan är satt till det önskade värdet
            if (updateCustomer.getOk() == false) {
                updateCustomer.setOk(true);
                System.out.println("Hittade kund med id: " + updateCustomer.getId() + ". Sattes till true");
            } else {
                updateCustomer.setOk(false);
                System.out.println("Hittade kund med id: " + updateCustomer.getId() + ". Sattes till false");
            }

            HttpClient client = HttpClient.newHttpClient();

            // Convert user object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(updateCustomer);

            // Create HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://javabl.systementor.se/api/rosa/blacklist/" + email)) // Replace with your actual API endpoint
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            // Send HttpRequest and get HttpResponse
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response status code
            if (response.statusCode() == 204) {
                // The user was successfully added to the blacklist
                return updateCustomer;
            } else {
                // There was an error adding the user to the blacklist
                System.out.println("Error adding user to blacklist: " + response.statusCode());
                return null;
            }
        } else {
            System.out.println("Hittade ingen kund med id: " + email + ", skickas till addCustomer");
            DetailedCustomerDto customer = customerService.findByEmail(email);

            return addCustomer(customer.getId());
        }
    }
}
