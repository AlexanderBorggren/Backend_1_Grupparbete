package com.example.grupparbete_backend_1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomType {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String roomSize;
    private int maxExtraBeds;
    private double pricePerNight;

    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updatedate;

    public RoomType(String roomSize, int maxExtraBeds, double pricePerNight) {
        this.roomSize = roomSize;
        this.maxExtraBeds = maxExtraBeds;
        this.pricePerNight=pricePerNight;
    }
}
