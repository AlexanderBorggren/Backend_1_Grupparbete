package com.example.grupparbete_backend_1.models;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "customers")
public class ContractCustomer {

    @Id
    @JacksonXmlProperty(localName = "id")
    private Long id;

    private String companyName;
    private String contactName;
    private String contactTitle;
    private String streetAddress;
    private String city;
    @JacksonXmlProperty(localName = "postalCode")
    private String postalCode;
    private String country;
    private String phone;
    @JacksonXmlProperty(localName = "fax")
    private String fax;


}
