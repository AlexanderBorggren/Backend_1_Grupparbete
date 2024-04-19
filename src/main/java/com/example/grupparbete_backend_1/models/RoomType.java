package com.example.grupparbete_backend_1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String roomSize;
    private Long maxExtraBeds;

    RoomType(String roomSize, Long maxExtraBeds) {
        this.roomSize = roomSize;
        this.maxExtraBeds = maxExtraBeds;
    }
}
