package com.example.grupparbete_backend_1.dto;

import com.example.grupparbete_backend_1.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private CustomerDto customer;
    private RoomDto room;

}