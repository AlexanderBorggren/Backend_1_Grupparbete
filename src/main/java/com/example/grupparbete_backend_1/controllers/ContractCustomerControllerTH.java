package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedContractCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedCustomerDto;
import com.example.grupparbete_backend_1.models.ContractCustomer;
import com.example.grupparbete_backend_1.repositories.ContractCustomerRepo;
import com.example.grupparbete_backend_1.services.ContractCustomerService;
import org.springframework.data.domain.Page;
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
import java.util.stream.Collectors;

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

    @RequestMapping("/contractcustomerview/{id}")
    public String viewCustomerDetails(@PathVariable Long id, Model model) {
        System.out.println("Requested customer ID: " + id);

        DetailedContractCustomerDto detailedContractCustomerDto = contractCustomerService.getById(id);

        System.out.println("Retrieved customer details: " + detailedContractCustomerDto);

        model.addAttribute("contractCustomer", detailedContractCustomerDto);
        return "contractCustomerView";
    }

    @RequestMapping(path="/")
    String viewAllSortSearch(Model model, @RequestParam(defaultValue = "1") int pageNo,
                   @RequestParam(defaultValue = "50") int pageSize,
                   @RequestParam(defaultValue = "companyName") String sortCol,
                   @RequestParam(defaultValue = "asc") String sortOrder,
                   @RequestParam(defaultValue = "") String q)
    {
        model.addAttribute("activeFunction", "home");
        q = q.trim();

        model.addAttribute("q",q);
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);

        model.addAttribute("contractCustomerTitle", "All contract customers");
        model.addAttribute("companyName", "Company name ");
        model.addAttribute("country", "Country ");
        model.addAttribute("contactName", "Contact name ");
        model.addAttribute("contactTitle", "Title ");

        if(!q.isEmpty()){
            Page<ContractCustomer> page = contractCustomerRepo.findAllByCompanyNameContainsOrContactNameContains(q,q,pageable);

            model.addAttribute("allContractCustomers", page);
            model.addAttribute("totalPages",page.getTotalPages());
            model.addAttribute("pageNo",pageNo);
            model.addAttribute("sortOrder", sortOrder.equals("asc") ? "desc" : "asc");

        }else if (!sortCol.isEmpty() && !sortOrder.isEmpty()){
            model.addAttribute("sortOrder", sortOrder.equals("asc") ? "desc" : "asc");
            Page<ContractCustomer> page = contractCustomerRepo.findAll(pageable);
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("totalPages",page.getTotalPages());
            model.addAttribute("allContractCustomers", page);
        }

        return "contractCustomers";
    }


}
