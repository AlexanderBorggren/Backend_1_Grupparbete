package com.example.grupparbete_backend_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String ssn;
    private String email;

    Customer(String name, String ssn, String email){
        this.name = name;
        this.ssn = ssn;
        this.email = email;
    }
}
