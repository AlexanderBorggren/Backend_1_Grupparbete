package com.example.grupparbete_backend_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedBookingDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestQuantity;
    private int extraBedsQuantity;
    private CustomerDto customer;
    private DetailedCustomerDto detailedCustomerDto;
    private RoomDto room;


    public DetailedBookingDto(LocalDate startDate, LocalDate endDate, int guestQuantity, int extraBedsQuantity, CustomerDto customer, RoomDto room) {
        this.id=id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestQuantity = guestQuantity;
        this.extraBedsQuantity = extraBedsQuantity;
        this.customer = customer;
        this.room = room;
    }


    public DetailedBookingDto( LocalDate startDate, LocalDate endDate, int guestQuantity, int extraBedsQuantity,DetailedCustomerDto detailedCustomerDto, RoomDto room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestQuantity = guestQuantity;
        this.extraBedsQuantity = extraBedsQuantity;
        this.detailedCustomerDto = detailedCustomerDto;
        this.room = room;
    }



}