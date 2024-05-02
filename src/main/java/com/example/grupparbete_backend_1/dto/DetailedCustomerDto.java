package com.example.grupparbete_backend_1.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedCustomerDto {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String ssn;
    private List<BookingDto> bookingDtoList;

    public DetailedCustomerDto(String name, String ssn, String email) {
        this.email = email;
        this.name = name;
        this.ssn = ssn;
        bookingDtoList = Collections.emptyList();
    }

}
