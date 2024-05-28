package com.example.grupparbete_backend_1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
 //   @FutureOrPresent (message = "Startdatumet måste vara efter gårdagens datum")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
  //  @Future (message = "Slutdatumet måste minst en dag i framtiden")
    private LocalDate endDate;

    @NotNull
    @Min(value = 1, message = "Antal gäster måste vara mer än 0")
    @Max(value = 4, message = "Antalet gäster sängar kan inte vara mer än 4")
    private int guestQuantity;

    @NotNull
    @Min(value = 0, message = "Antalet extra sängar kan inte vara mindre än 0")
    @Max(value = 2, message = "Antalet extra sängar kan inte vara mer än 2")
    private int extraBedsQuantity;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Room room;
    private Double totalPrice;

    @OneToMany
    private List<Discount> discount;


    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updatedate;

    public Booking (LocalDate startDate, LocalDate endDate, int guestQuantity, int extraBedsQuantity, Customer customer, Room room, Double totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestQuantity = guestQuantity;
        this.extraBedsQuantity = extraBedsQuantity;
        this.customer = customer;
        this.room = room;
        this.totalPrice = totalPrice;
    }

    public Booking (Long id,LocalDate startDate, LocalDate endDate, int guestQuantity, int extraBedsQuantity, Customer customer, Room room, Double totalPrice) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestQuantity = guestQuantity;
        this.extraBedsQuantity = extraBedsQuantity;
        this.customer = customer;
        this.room = room;
        this.totalPrice = totalPrice;
    }
    public Booking (Long id,LocalDate startDate, LocalDate endDate, int guestQuantity, int extraBedsQuantity, Customer customer, Room room) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestQuantity = guestQuantity;
        this.extraBedsQuantity = extraBedsQuantity;
        this.customer = customer;
        this.room = room;
    }
}

