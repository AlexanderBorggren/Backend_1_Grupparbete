package com.example.grupparbete_backend_1.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("roomOpened")
public class RoomOpened extends EventBase {

    // For future use
    public String roomOpenedByUser;

    @Override
    public void setMessage() {
        super.setMessage("Room opened");
    }


}