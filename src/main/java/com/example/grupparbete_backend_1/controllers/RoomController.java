package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.Room;
import com.example.grupparbete_backend_1.repositories.BookingRepo;
import com.example.grupparbete_backend_1.repositories.CustomerRepo;
import com.example.grupparbete_backend_1.repositories.RoomRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoomController {

    private final RoomRepo roomRepo;

    RoomController(RoomRepo roomRepo){
            this.roomRepo = roomRepo;
        }
    @PostMapping("addRoom")
    public void addRoom(@RequestBody Room room){
        roomRepo.save(room);
    }
}
