package com.example.grupparbete_backend_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedRoomTypeDto {
    private Long id;
    private String roomSize;
    private int maxExtraBeds;
}
