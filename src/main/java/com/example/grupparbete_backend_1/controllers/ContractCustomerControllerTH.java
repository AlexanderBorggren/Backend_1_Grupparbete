package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.ContractCustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@Validated
@RequestMapping("/contractcustomers")
public class ContractCustomerControllerTH {

    ContractCustomerService contractCustomerService;
    ContractCustomerRepo contractCustomerRepo;

    public ContractCustomerControllerTH(ContractCustomerService contractCustomerService,ContractCustomerRepo contractCustomerRepo){
        this.contractCustomerService=contractCustomerService;
        this.contractCustomerRepo=contractCustomerRepo;
    }

    @RequestMapping("/all")
    public String getAll(Model model) {
        List<DetailedContractCustomerDto> k = contractCustomerService.getAllContractCustomers();
        model.addAttribute("allContractCustomers", k);
        model.addAttribute("contractCustomerTitle", "All contract customers");
        model.addAttribute("companyName", "Company name ");
        model.addAttribute("country", "Country ");
        model.addAttribute("contactName", "Contact name ");
        model.addAttribute("contactTitle", "Title ");
        model.addAttribute("sortOrder", "asc");
        return "contractCustomers";
    }

    @RequestMapping("/contractcustomerview/{id}")
    public String viewCustomerDetails(@PathVariable Long id, Model model) {
        System.out.println("Requested customer ID: " + id);

        DetailedContractCustomerDto detailedContractCustomerDto = contractCustomerService.getById(id);

        System.out.println("Retrieved customer details: " + detailedContractCustomerDto);

        model.addAttribute("contractCustomer", detailedContractCustomerDto);
        return "contractCustomerView";
    }


    @GetMapping(path="/")
    String sorting(Model model, @RequestParam(defaultValue = "1") int pageNo,
                   @RequestParam(defaultValue = "10") int pageSize,
                   @RequestParam(defaultValue = "companyName") String sortCol,
                   @RequestParam(defaultValue = "asc") String sortOrder,
                   @RequestParam(defaultValue = "") String g)
    {
        model.addAttribute("activeFunction", "home");

        g = g.trim();

        model.addAttribute("g",g);
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);

        model.addAttribute("contractCustomerTitle", "All contract customers");
        model.addAttribute("companyName", "Company name ");
        model.addAttribute("country", "Country ");
        model.addAttribute("contactName", "Contact name ");
        model.addAttribute("contactTitle", "Title ");

        if(!g.isEmpty()){
            if(sortCol.equals("companyName")) {
                model.addAttribute("allContractCustomers", contractCustomerRepo.findAllByCompanyNameContains(g,sort));
            } else if (sortCol.equals("contactName")){
                model.addAttribute("allContractCustomers", contractCustomerRepo.findAllByContactNameContains(g,sort));
            } else {
                model.addAttribute("allContractCustomers", contractCustomerRepo.findAllByContactTitleContains(g,sort));
            }
            model.addAttribute("totalPages",1);
            model.addAttribute("pageNo",1);
        }else{
            model.addAttribute("sortOrder", sortOrder.equals("asc") ? "desc" : "asc");
            List<ContractCustomer> page = contractCustomerRepo.findAll(sort);
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("allContractCustomers", page);
        }
        return "contractCustomers";
    }


}
