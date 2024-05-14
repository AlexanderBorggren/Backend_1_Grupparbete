package com.example.grupparbete_backend_1.Events;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("roomOpened")
public class RoomOpened extends EventBase {
    public String RoomNo;
}