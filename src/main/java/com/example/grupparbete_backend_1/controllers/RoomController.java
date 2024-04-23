package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.RoomDto;
import com.example.grupparbete_backend_1.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("addRoom")
    public void addRoom(@RequestBody RoomDto room){
        roomService.addRoom(room);
    }
    @RequestMapping("Rooms")
    public List<RoomDto> getAllRooms(){
        return roomService.getAllRoom();
    }
}
