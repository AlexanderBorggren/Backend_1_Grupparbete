package com.example.grupparbete_backend_1.Events;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("roomCleaningFinished")
public class RoomCleaningFinished extends EventBase {

        public String CleaningByUser;
    }

