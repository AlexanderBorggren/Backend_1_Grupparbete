package com.example.grupparbete_backend_1.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractCustomer {

   /* @Id
    @JacksonXmlProperty(localName = "id")
    @JsonIgnore
    private Long id;*/
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
