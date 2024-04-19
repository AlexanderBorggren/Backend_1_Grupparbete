package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.models.RoomType;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import com.example.grupparbete_backend_1.repositories.RoomTypeRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoomTypeController {

    private final RoomTypeRepo roomTypeRepo;

    RoomTypeController(RoomTypeRepo roomTypeRepo){
            this.roomTypeRepo = roomTypeRepo;
        }
}
