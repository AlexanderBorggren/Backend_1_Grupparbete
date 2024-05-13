package com.example.grupparbete_backend_1.controllers;


import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.services.BlacklistService;
import com.example.grupparbete_backend_1.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/blacklist")
@Controller
public class BlacklistController {

    private final BlacklistService blacklistService;
    private final CustomerService customerService;

    public BlacklistController(CustomerService customerService, BlacklistService blacklistService) {
        this.customerService = customerService;
        this.blacklistService = blacklistService;}



    /*@RequestMapping("/all")
    public String getAll(Model model) {
        List<DetailedCustomerDto> k = customerService.getAllCustomer();
        model.addAttribute("allCustomers", k);
        model.addAttribute("customerTitle", "All customers");
        model.addAttribute("name", "Name: ");
        model.addAttribute("ssn", "SSN: ");
        model.addAttribute("email", "Email: ");
        model.addAttribute("customerId", "CustomerID: ");
        return "customers";
    }
    */


    @RequestMapping("/adminBlacklistView")
    public String adminBlacklistView(Model model) {

        List<DetailedCustomerDto> k = customerService.getAllCustomer();
        model.addAttribute("allCustomers", k);
        model.addAttribute("customerTitle", "All customers");
        model.addAttribute("name", "Name: ");
        model.addAttribute("ssn", "SSN: ");
        model.addAttribute("email", "Email: ");
        model.addAttribute("customerId", "CustomerID: ");

        return "adminBlacklistView";
    }


    @RequestMapping(path = "/addToBlacklist/{id}/")
    public ResponseEntity<BlacklistedCustomerDto> addCustomerToBlacklist(@PathVariable Long id) {

        System.out.println("Inne i addCustomerToBlacklist med: " + id);

        try {
            BlacklistedCustomerDto user = blacklistService.addCustomer(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().build(); // or another appropriate HTTP status
            }
        } catch (Exception e) {
            System.out.println("här gick det fel");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

    @RequestMapping(path= "/updateBlacklist/{email}/")
    public ResponseEntity<BlacklistedCustomerDto> updateCustomerToBlacklist(@PathVariable String email) {

        try {
            BlacklistedCustomerDto user = blacklistService.updateCustomer(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e){
            System.out.println("Något gick fel error " + ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }




    }



     /*   HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/rosa/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("email=" + email + "&name=" + name + "&ok=" + ok))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

*/

}
