package com.example.grupparbete_backend_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn
    private RoomType roomType;

    Room(RoomType roomType, Long timestamp) {
        this.roomType = roomType;
    }
}
