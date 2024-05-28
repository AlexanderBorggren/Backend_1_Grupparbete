package com.example.grupparbete_backend_1.dto;


import lombok.Data;

@Data
public class BlacklistedCustomerDto {

    private Long id;
    private String name;
    private String email;
    private String group;
    private String created;
    private Boolean ok;
    private String BlacklistMessage;

}
