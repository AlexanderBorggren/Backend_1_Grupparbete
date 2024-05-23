package com.example.grupparbete_backend_1.controllers;


import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.services.BlacklistService;
import com.example.grupparbete_backend_1.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URISyntaxException;
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



    @RequestMapping("/externalBlacklistView")
    public String checkBlacklistView(Model model) throws IOException, URISyntaxException, InterruptedException {

        List<BlacklistedCustomerDto> k = blacklistService.getBlacklistedCustomers();
        model.addAttribute("allOnList", k);
        model.addAttribute("blacklistTitle", "All customer on blacklist");
        model.addAttribute("id", "Id: ");
        model.addAttribute("email", "Email: ");
        model.addAttribute("name", "Name: ");
        model.addAttribute("group", "Group: ");
        model.addAttribute("created", "Created: ");
        model.addAttribute("ok", "Ok: ");

        return "externalBlacklistView";
    }




    @RequestMapping(path = "/addToBlacklist/{id}/")
    public RedirectView addCustomerToBlacklist(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        System.out.println("Inne i addCustomerToBlacklist med: " + id);

        try {
            BlacklistedCustomerDto user = blacklistService.addCustomerFromList(id);
            if (user != null) {
                redirectAttributes.addFlashAttribute("message", "Customer added to blacklist");
                return new RedirectView("/blacklist/adminBlacklistView");
            } else {
                redirectAttributes.addFlashAttribute("message", "Customer not added to blacklist");
                return new RedirectView("/blacklist/adminBlacklistView");
            }
        } catch (Exception e) {
            System.out.println("här gick det fel");

            return new RedirectView("/blacklist/adminBlacklistView");

        }

    }


    @RequestMapping(path= "/updateBlacklist/{email}/")
    public RedirectView updateCustomerToBlacklist(@PathVariable String email, RedirectAttributes redirectAttributes) {

        try {
            BlacklistedCustomerDto user = blacklistService.updateCustomer(email);
            if (user != null) {
                redirectAttributes.addFlashAttribute("message", "Customer updated with blacklist status OK: " + user.getOk()
                        + " Server responded with: "+  ResponseEntity.status(HttpStatus.OK).build()) ;
                return new RedirectView("/blacklist/adminBlacklistView");
            }
            else{
                redirectAttributes.addFlashAttribute("message", "Customer not found" + user.getEmail());
                return new RedirectView("/blacklist/adminBlacklistView");
            }

        }
        catch (Exception e){
            System.out.println("Något gick fel error " + ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            return new RedirectView("/blacklist/adminBlacklistView");
        }




    }

    @RequestMapping("/addBlacklistView")
    public String createCustomerByForm(Model model) {
        return "addToBlacklistForm";
    }


    @RequestMapping(path = "/addNewCustomerToBlacklist")
    public RedirectView addNewCustomerToBlacklist(@Valid @RequestParam String name, String email, RedirectAttributes redirectAttributes) {

        System.out.println("Inne i addNewCustomerToBlacklist med: " + name +" "+ email);

        try {
            BlacklistedCustomerDto user = blacklistService.addNewCustomer(name, email);
            if (user != null) {
                redirectAttributes.addFlashAttribute("message", "Person "+ name + " Email: " + email + " have been added to blacklist");
                return new RedirectView("/blacklist/adminBlacklistView");
            } else {
                redirectAttributes.addFlashAttribute("message", "Person not added to blacklist");
                return new RedirectView("/blacklist/adminBlacklistView");
            }
        } catch (Exception e) {
            System.out.println("här gick det fel" + ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            return new RedirectView("/blacklist/adminBlacklistView");

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