package com.example.grupparbete_backend_1.Events;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("roomCleaningStarted")
public class RoomCleaningStarted extends EventBase {
    public String RoomNo;
    public String CleaningByUser;
}
