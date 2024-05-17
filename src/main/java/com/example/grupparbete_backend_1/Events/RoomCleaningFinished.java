package com.example.grupparbete_backend_1.Events;


import com.example.grupparbete_backend_1.models.Room;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("roomCleaningFinished")
public class RoomCleaningFinished extends EventBase {

    public String CleaningByUser;

    @Override
    public String getUser() {
        return this.CleaningByUser;
    }

    @Override
    public void setMessage() {
        super.setMessage("Room cleaning finished");
    }


}

