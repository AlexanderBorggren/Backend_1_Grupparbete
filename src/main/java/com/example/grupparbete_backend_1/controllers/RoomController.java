package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoomController {

    private final RoomRepo roomRepo;

        RoomController(RoomRepo roomRepo){
            this.roomRepo = roomRepo;
        }
}
