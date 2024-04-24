package com.example.grupparbete_backend_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedCustomerDto {

    private Long id;
    private String email;
    private String name;
    private String ssn;
    private List<BookingDto> bookingDtoList;



}
