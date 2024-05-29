package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.configuration.IntegrationProperties;
import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.BlacklistCheckResponse;
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


    private IntegrationProperties properties;

    private CustomerService customerService;


    @Autowired
    public BlacklistServiceImpl(CustomerService customerService, IntegrationProperties properties){
        this.customerService = customerService;
        this.properties = properties;
    }

    public BlacklistServiceImpl(){}


   @Override
    public boolean isBlacklistOk(String email){

       HttpClientProvider httpClientProvider = new HttpClientProvider();

       HttpResponse<String> response = httpClientProvider.sendHttpRequest(properties.getBlacklist().getUrlCheck() + email,"GET", null);


       if (response != null && response.statusCode() == 200) {
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
    public List<BlacklistedCustomerDto> getBlacklistedCustomers() throws IOException{

        HttpClientProvider httpClientProvider = new HttpClientProvider();

        HttpResponse<String> response = httpClientProvider.sendHttpRequest(properties.getBlacklist().getUrl(), "GET", null);


        if (response != null && response.statusCode() == 200) {

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
    public BlacklistedCustomerDto addCustomerFromList(Long id) throws IOException, InterruptedException, URISyntaxException {


        List<BlacklistedCustomerDto> blacklistedCustomers = getBlacklistedCustomers();
        DetailedCustomerDto customer = customerService.findById(id);

        Optional<BlacklistedCustomerDto> matchingCustomer = blacklistedCustomers.stream()
                .filter(blacklistedCustomer -> blacklistedCustomer.getEmail().equals(customer.getEmail()))
                .findFirst();
        System.out.println(matchingCustomer.isPresent());
        System.out.println(matchingCustomer.isEmpty());

        if((matchingCustomer.isEmpty())) {
            System.out.println("no matching customer, adding to blacklist");
            BlacklistedCustomerDto user = new BlacklistedCustomerDto();

            user.setId(customer.getId());
            user.setName(customer.getName());
            user.setEmail(customer.getEmail());
            user.setGroup("rosa");
            user.setCreated(Instant.now().toString());
            user.setOk(false);
            user.setBlacklistMessage("Added to blacklist");

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonInputString = objectMapper.writeValueAsString(user);

                HttpClientProvider httpClientProvider = new HttpClientProvider();
                HttpResponse<String> response = httpClientProvider.sendHttpRequest(properties.getBlacklist().getUrl(), "POST", jsonInputString);


            // Check the response status code
            if (response != null && response.statusCode() == 200) {
                // The user was successfully added to the blacklist
                return user;
            } else {
                // There was an error adding the user to the blacklist
                System.out.println("Error adding user to blacklist: " + response.statusCode());
                return null;
            }

        }

        System.out.println("Customer found in blacklist, updating");
       return updateCustomer(customer.getEmail());
    }
    @Override
    public BlacklistedCustomerDto updateCustomer(String email) throws IOException, InterruptedException, URISyntaxException {

        List<BlacklistedCustomerDto> blacklistedCustomers = getBlacklistedCustomers();

        Optional<BlacklistedCustomerDto> optionalCustomer = blacklistedCustomers.stream()
                .filter(blacklistedCustomer -> blacklistedCustomer.getEmail().equals(email))
                .findFirst();

        if (optionalCustomer.isPresent()) {
            BlacklistedCustomerDto updateCustomer = optionalCustomer.get();
            System.out.println("hittade " + updateCustomer.getId() + updateCustomer.getEmail() + updateCustomer.getName());

            // Kontrollera om kundens ok-status redan är satt till det önskade värdet
            if (!updateCustomer.getOk()) {
                updateCustomer.setOk(true);
                updateCustomer.setBlacklistMessage(updateCustomer.getName() + " is no longer blacklisted");
                System.out.println("Hittade kund med id: " + updateCustomer.getId() + ". Sattes till true");
            } else {
                updateCustomer.setOk(false);
                updateCustomer.setBlacklistMessage(updateCustomer.getName() +" is now blacklisted");
                System.out.println("Hittade kund med id: " + updateCustomer.getId() + ". Sattes till false");
            }


            HttpClient client = HttpClient.newHttpClient();

                // Convert user object to JSON
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonInputString = objectMapper.writeValueAsString(updateCustomer);

            HttpClientProvider httpClientProvider = new HttpClientProvider();
            HttpResponse<String> response = httpClientProvider.sendHttpRequest(properties.getBlacklist().getUrl() + email, "PUT", jsonInputString);


/*
            // Create HttpRequest
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://javabl.systementor.se/api/rosa/blacklist/" + email)) // Replace with your actual API endpoint
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(jsonInputString))
                        .build();


                // Send HttpRequest and get HttpResponse
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());*/


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

            return addCustomerFromList(customer.getId());
        }
    }

    @Override
    public BlacklistedCustomerDto addNewCustomer(String name, String email) throws IOException, InterruptedException, URISyntaxException {

        List<BlacklistedCustomerDto> blacklistedCustomers = getBlacklistedCustomers();

        boolean isUserOnBlacklist = blacklistedCustomers.stream()
                        .anyMatch(blacklistedCustomer -> blacklistedCustomer.getEmail().equals(email));

        if(isUserOnBlacklist) {
            System.out.println("Customer already on blacklist");

            return updateCustomer(email);

        }
        else{

            BlacklistedCustomerDto user = new BlacklistedCustomerDto();
            System.out.println("Customer added to blacklist");

            user.setName(name);
            user.setEmail(email);
            user.setGroup("rosa");
            user.setCreated(Instant.now().toString());
            user.setOk(false);
            user.setBlacklistMessage("Added to blacklist");


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

    }
}
