package com.example.grupparbete_backend_1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;


import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotEmpty(message = "Namnet får inte vara tomt")
    @Size(min = 1, max = 50, message = "Namnet får inte vara längre än 50 tecken")
    private String name;

    @NotNull
    @NotEmpty(message = "Personnumret får inte vara tomt")
    @Pattern(regexp = "^\\d{10}$", message = "Personnumret måste ha exakt 10 siffror")
    @Column(unique = true)
    private String ssn;

    @Email (message = "E-posten är ej giltig")
    @NotNull
    @NotEmpty(message = "E-post får inte vara tomt")
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Booking> bookingList;

    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updatedate;


    public Customer(String name, String ssn, String email){
        this.name = name;
        this.ssn = ssn;
        this.email = email;
    }

}
