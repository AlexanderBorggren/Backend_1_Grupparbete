package com.example.grupparbete_backend_1.Events;


import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
@JsonTypeName("roomCleaningStarted")
public class RoomCleaningStarted extends EventBase {

    public String cleaningByUser;
}
