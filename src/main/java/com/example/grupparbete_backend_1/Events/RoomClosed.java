package com.example.grupparbete_backend_1.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("roomClosed")
public class RoomClosed extends EventBase{

    //For future use
    public String roomClosedByUser;

    @Override
    public void setMessage() {
        super.setMessage("Room closed");
    }

}