package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;


@Controller
@RequestMapping("/customer")
public class CustomerControllerTH{

    CustomerService customerService;

    public CustomerControllerTH(CustomerService customerService){
        this.customerService=customerService;
    }

   /* @PostMapping("submitUpdate")
    public String submitUpdate(@RequestParam String name,
                               @RequestParam String ssn,
                               @RequestParam String email, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("ssn", ssn);
        model.addAttribute("email", email);
        System.out.println(name + ssn + email);
        return "index.html";
    }
   @RequestMapping("/test")
    public String testHTML(Model model){
        return "index.html";
    }*/

    @RequestMapping("/all")
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

    @RequestMapping(path = "/deleteById/{id}/")
    public String deleteCustomer(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        String message = customerService.deleteCustomer(id);
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/customer/all";
    }

    @RequestMapping("/editByView/{id}/")
    public String createByForm(@PathVariable Long id, Model model) {
        //System.out.println("hej");
        DetailedCustomerDto customer = customerService.findById(id);

        //TODO - HANDLE NULL CUSTOMER


        model.addAttribute("customer", customer);
        return "updateCustomerForm";
    }

    @PostMapping("/update")
    public String updateCustomer(@Valid Model model, DetailedCustomerDto c, RedirectAttributes redirectAttributes) {
        customerService.addCustomer(c);
        //List<DetailedCustomerDto> k = customerService.getAllCustomer();
        //model.addAttribute("allCustomers", k);
        model.addAttribute("name", "name");
        model.addAttribute("ssn", "ssn");
        model.addAttribute("email", "email");

        String feedbackMessage = "Customer id " + c.getId() + " with name " + c.getName() + " has been updated.";
        redirectAttributes.addFlashAttribute("updateCustomerFeedbackMessage", feedbackMessage);

        return "redirect:/customer/all";
    }
    @RequestMapping("/addCustomerView")
    public String createCustomerByForm(Model model) {
        return "addCustomerForm";
    }
    @RequestMapping("/addCustomerNewView")
    public String createCustomerByFormNew(Model model) {
        return "addCustomerNewForm";
    }


    @PostMapping("/addCustomer")
    public String addCustomer(@Valid @RequestParam String name, @RequestParam String ssn, @RequestParam String email, Model model) {
        model.addAttribute("name", "name");
        model.addAttribute("ssn", "ssn");
        model.addAttribute("email", "email");
        customerService.addCustomer(new DetailedCustomerDto(name, ssn, email));

        return "redirect:/customer/all";
    }
    @PostMapping("/addCustomerNew")
    public String addCustomerNew(@Valid @RequestParam String name, @RequestParam String ssn, @RequestParam String email, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("name", "name");
        model.addAttribute("ssn", "ssn");
        model.addAttribute("email", "email");
        customerService.addCustomer(new DetailedCustomerDto(name, ssn, email));
        DetailedCustomerDto customer = customerService.findBySsn(ssn);
        Long customerId = customer.getId();
        if (customerService.findBySsn(ssn) == null) {

            return "redirect:/customer/all";
        }
        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:/booking/bookingByView/{customerId}/";
    }




}
