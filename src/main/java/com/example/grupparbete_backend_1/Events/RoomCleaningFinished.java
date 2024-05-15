package com.example.grupparbete_backend_1.Events;


import com.example.grupparbete_backend_1.models.Room;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@JsonTypeName("roomCleaningFinished")
public class RoomCleaningFinished extends EventBase {

    public String cleaningByUser;

    RoomCleaningFinished(Long id, String message, Long roomNo, LocalDateTime timeStamp, Timestamp updatedate, String cleaningByUser) {
        super(id, message, roomRepo roomNo, timeStamp, updatedate);
        this.cleaningByUser = cleaningByUser;
    }
}

