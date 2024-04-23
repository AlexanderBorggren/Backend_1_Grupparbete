package com.example.grupparbete_backend_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@Builder
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
    private int guestQuantity;
    private int extraBedsQuantity;

    @ManyToOne()
    /*@ManyToOne(cascade = CascadeType.ALL)*/
    @JoinColumn
    private Customer customer;
    @OneToOne
    @JoinColumn
    private Room room;
    @CreationTimestamp
    private Timestamp regdate;
    @UpdateTimestamp
    private Timestamp updatedate;

    public Booking (LocalDate startDate, LocalDate endDate, int guestQuantity, int extraBedsQuantity, Customer customer, Room room, Timestamp regdate, Timestamp updatedate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestQuantity = guestQuantity;
        this.extraBedsQuantity = extraBedsQuantity;
        this.customer = customer;
        this.room = room;
    }
}

