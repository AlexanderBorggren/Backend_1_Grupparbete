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
    private String guestQuantity;
    private int extraBedsQuantity;

    @ManyToOne
    @JoinColumn
    private Customer customer;
    @OneToOne
    @JoinColumn
    private Room room;

    Booking (LocalDate startDate, LocalDate endDate, String guestQuantity, int extraBedsQuantity, Customer customer, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestQuantity = guestQuantity;
        this.extraBedsQuantity = extraBedsQuantity;
        this.customer = customer;
        this.room = room;
    }
}

