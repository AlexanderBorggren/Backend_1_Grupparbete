package com.example.grupparbete_backend_1.models;

import com.example.grupparbete_backend_1.Events.EventBase;
import jakarta.persistence.*;
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
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private RoomType roomType;

    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updatedate;


    public Room(RoomType roomType, Timestamp regdate, Timestamp updatedate) {
        this.roomType = roomType;
        this.regdate = regdate;
        this.updatedate = updatedate;
    }
    public Room(RoomType roomType) {
        this.roomType = roomType;
    }
}
