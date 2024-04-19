package com.example.grupparbete_backend_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String nrCustomer;
    private int extraBeds;

    @ManyToOne
    @JoinColumn
    private Customer customer_Id;
    @OneToOne
    @JoinColumn
    private Room room_Id;

    Booking (LocalDate startDate, LocalDate endDate, String nrCustomer, int extraBeds, Customer customer_Id, Room room_Id) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.nrCustomer = nrCustomer;
        this.extraBeds = extraBeds;
        this.customer_Id = customer_Id;
        this.room_Id = room_Id;
    }
    //Customer customer
}

