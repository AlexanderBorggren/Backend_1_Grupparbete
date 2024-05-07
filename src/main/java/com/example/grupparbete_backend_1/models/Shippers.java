package com.example.grupparbete_backend_1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

public class Shippers {

    //Skapa Shippers-tabellen i databasen med fälten Id, CompanyName och Phone.

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    @JsonProperty("id")
    private Long external_Shippers_Id;
    @NotNull
    @NotEmpty
    private String companyName;
    private String phone;
    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updatedate;
}

