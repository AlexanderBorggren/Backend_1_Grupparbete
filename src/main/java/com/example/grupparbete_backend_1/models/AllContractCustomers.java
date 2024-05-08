package com.example.grupparbete_backend_1.models;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;
@Data
@JacksonXmlRootElement(localName = "allcustomers")
public class AllContractCustomers {

    @JacksonXmlProperty(localName = "customers")
    public List<ContractCustomer> contractCustomers;


}
