package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.services.ContractCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@Validated
@RequestMapping("/contractcustomers")
public class ContractCustomerControllerTH {

    ContractCustomerService contractCustomerService;

    public ContractCustomerControllerTH(ContractCustomerService contractCustomerService){
        this.contractCustomerService=contractCustomerService;
    }

    @RequestMapping("/all")
    public String getAll(Model model) {
        List<DetailedContractCustomerDto> k = contractCustomerService.getAllContractCustomers();
        model.addAttribute("allContractCustomers", k);
        model.addAttribute("contractCustomerTitle", "All contract customers");
        model.addAttribute("companyName", "Company name: ");
        model.addAttribute("country", "Country: ");
        model.addAttribute("contactName", "Contact name: ");
        model.addAttribute("contactTitle", "Title: ");
        return "contractCustomers";
    }

    @RequestMapping("/contractcustomerview/{id}/")
    public String createCustomerByForm(@PathVariable Long id, Model model) {
        DetailedContractCustomerDto detailedContractCustomerDto = contractCustomerService.findById(id);
        model.addAttribute("contractCustomers", detailedContractCustomerDto);
        model.addAttribute("contractCustomerTitle", "Contract customer details");
        model.addAttribute("companyName", "Company name: ");
        model.addAttribute("country", "Country: ");
        model.addAttribute("contactName", "Contact name: ");
        model.addAttribute("contactTitle", "Title: ");
        model.addAttribute("streetAddress", "Address: ");
        model.addAttribute("postalCode", "Postal code: ");
        model.addAttribute("country", "Country: ");
        model.addAttribute("phone", "Phone number: ");

        return "contractCustomerView";
    }

  /*  @RequestMapping("/contractcustomerview/{id}/")
    public String contractCustomerView(@PathVariable Long id, Model model) {
        DetailedContractCustomerDto customer = contractCustomerService.findById(id);
        //TODO - HANDLE NULL CUSTOMER

        model.addAttribute("contractCustomer", contractCustomer);
        return "contractCustomers";
    }
*/

}
