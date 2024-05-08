package com.example.grupparbete_backend_1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@JacksonXmlRootElement(localName = "allcustomers")
public class AllContractCustomers {

    @JacksonXmlProperty(localName = "customers")
    public List<ContractCustomer> contractCustomers;


}
