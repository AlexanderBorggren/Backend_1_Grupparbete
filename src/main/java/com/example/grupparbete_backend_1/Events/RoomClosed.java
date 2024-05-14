package com.example.grupparbete_backend_1.Events;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("roomClosed")
public class RoomClosed extends EventBase{
    public String RoomNo;
}