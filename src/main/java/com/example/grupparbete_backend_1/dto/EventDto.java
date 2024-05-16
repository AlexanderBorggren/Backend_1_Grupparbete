package com.example.grupparbete_backend_1.dto;

import com.example.grupparbete_backend_1.models.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private Long id;
    private String message;
    private Room RoomNo;
    private String user;
    private LocalDateTime TimeStamp;
}