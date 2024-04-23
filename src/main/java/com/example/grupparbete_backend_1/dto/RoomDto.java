package com.example.grupparbete_backend_1.dto;

import com.example.grupparbete_backend_1.models.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private Long id;
    private DetailedRoomTypeDto roomType;
}
