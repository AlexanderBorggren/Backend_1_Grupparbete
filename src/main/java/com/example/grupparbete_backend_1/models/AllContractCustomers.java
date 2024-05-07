package com.example.grupparbete_backend_1.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;
@Data
@JacksonXmlRootElement(localName = "allcustomers")
public class AllContractCustomers {
    public List<ContractCustomer> contractCustomers;
}
