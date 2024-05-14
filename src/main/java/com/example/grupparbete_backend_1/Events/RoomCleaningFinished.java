package com.example.grupparbete_backend_1.Events;


import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@JsonTypeName("roomCleaningFinished")
@Entity
public class RoomCleaningFinished extends EventBase {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    public String CleaningByUser;
}

