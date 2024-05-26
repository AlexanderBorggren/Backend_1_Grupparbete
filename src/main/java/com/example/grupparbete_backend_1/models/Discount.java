package com.example.grupparbete_backend_1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.validation.annotation.Validated;

import java.awt.print.Book;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class Discount {
    @Id
    @GeneratedValue
    private Long id;

    private Double discountValue;
    @ManyToOne
    private Booking booking;

    public Discount(Double discountValue, Booking booking){
        this.discountValue=discountValue;
        this.booking=booking;
    }

}
